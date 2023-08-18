package com.example.ics.controller;

import com.example.ics.dto.ClovaOCRResponse;
import com.example.ics.service.ChatGPTService;
import com.example.ics.service.ClovaOCRService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/image")
public class ClovalOCRController {
    private final ChatGPTService chatGPTService;
    private final ClovaOCRService clovaOCRService;

    @GetMapping("/convert")
    public String uploadForm() {
        return "imageToText";
    }

    @PostMapping("/convert")
    public String handleImage(@RequestPart MultipartFile[] imageFile, Model model) throws Exception {
        Map<String, String> ocrResults = new HashMap<>();
        for (MultipartFile multipartFile : imageFile) {
            String[] imgMetaInfo = multipartFile.getContentType().split("/");
            if (!imgMetaInfo[0].equals("image")) {
                throw new Exception();
            }
            ocrResults.put("data:" + multipartFile.getContentType() + ";base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()), clovaOCRService.ocrImage(multipartFile));
        }
        model.addAttribute("ocrResults", ocrResults);
        return "imageToText";
    }

    @GetMapping("/summary")
    public String sumaryForm(Model model) {
        model.addAttribute("textContent", "");
        model.addAttribute("summaryContent", null);
        return "summaryTextForm";
    }

    @PostMapping("/summary")
    public String summaryContent(@RequestParam("textContent") String textContent, Model model) {
        long start = System.currentTimeMillis();
        Mono<String> summaryContentMono = chatGPTService.summaryContent(textContent);


        model.addAttribute("textContent", textContent);
        model.addAttribute("summaryResult", summaryContentMono.block());
        long end = System.currentTimeMillis();
        log.info("gwj : take time = " + (end - start) / 1000);
        return "summaryTextForm";
    }
//    @PostMapping("/summary")
//    public String summaryContent(@RequestParam("textContent") String textContent, Model model) {
//        long start = System.currentTimeMillis();
//        Mono<String> summaryContentMono = chatGPTService.summaryContent(textContent);
//
//        model.addAttribute("textContent", textContent);
//        summaryContentMono.subscribe(summaryResult -> {
//                    model.addAttribute("summaryResult", summaryResult);
//                    log.info("gwj : received = " + summaryResult);
//                },
//                error -> {
//                    log.info("gwj : error = " + error);
//                    model.addAttribute("summaryResult", "");
//                });
//        long end = System.currentTimeMillis();
//        log.info("gwj : take time = " + (end - start) / 1000);
//        return "summaryTextForm";
//    }
}

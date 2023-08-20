package com.example.ics.controller;

import com.example.ics.dto.Clova.ClovaImageType;
import com.example.ics.dto.chatGPT.ChatGPTResponse;
import com.example.ics.service.ChatGPTService;
import com.example.ics.service.ClovaOCRService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

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
    public String handleImage(@RequestPart("imageFiles") List<MultipartFile> imageFiles, Model model) throws Exception {
        long start = System.currentTimeMillis();
        String result = "";
        for (MultipartFile imageFile : imageFiles) {
            if (ClovaImageType.getExtensionFromMimeType(imageFile.getContentType()) == null) {
                throw new Exception("gwj : " + imageFile.getOriginalFilename() + " file is out of format");
            }

            // response test
//            result += new ObjectMapper().writeValueAsString(clovaOCRService.ocrImage(imageFile).block());

            // 동기
            result += clovaOCRService.ocrImageToBlock(clovaOCRService.ocrImage(imageFile));

            // 비동기
//            result += clovaOCRService.ocrImageToNonBlock(clovaOCRService.ocrImage(imageFile));
        }
        model.addAttribute("ocrResults", result);

        long end = System.currentTimeMillis();
        log.info("gwj : Clova take time = " + (end - start) / 1000);

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
        Mono<ChatGPTResponse> summaryContentMono = chatGPTService.summaryContent(textContent);
        model.addAttribute("textContent", textContent);
        // 동기
        model.addAttribute("summaryResult", summaryContentMono.block().getChoices().get(0).getMessage().getContent());

        // 비동기
//        summaryContentMono.subscribe(summaryResult -> {
//                    model.addAttribute("summaryResult", summaryResult.getChoices().get(0).getMessage().getContent());
//                    log.info("gwj : received = " + summaryResult.getChoices().get(0).getMessage().getContent());
//                },
//                error -> {
//                    log.info("gwj : error = " + error);
//                    model.addAttribute("summaryResult", "");
//                });

        long end = System.currentTimeMillis();
        log.info("gwj : chatGPT take time = " + (end - start) / 1000);
        return "summaryTextForm";
    }
}
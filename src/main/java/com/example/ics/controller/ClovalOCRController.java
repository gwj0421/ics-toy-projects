package com.example.ics.controller;

import com.example.ics.dto.Clova.ClovaImageType;
import com.example.ics.service.ClovaOCRService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/image")
public class ClovalOCRController {
    private final ClovaOCRService clovaOCRService;

    public ClovalOCRController(ClovaOCRService clovaOCRService) {
        this.clovaOCRService = clovaOCRService;
    }

    @GetMapping("/extractTextFromImage")
    public String uploadForm() {
        return "extractTextFromImageForm";
    }

    @PostMapping("/extractTextFromImage")
    public String handleImage(@RequestPart("imageFiles") List<MultipartFile> imageFiles, Model model) throws Exception {
        long start = System.currentTimeMillis();
        String result = "";
        for (MultipartFile imageFile : imageFiles) {
            if (ClovaImageType.getExtensionFromMimeType(imageFile.getContentType()) == null) {
                throw new Exception("gwj : " + imageFile.getOriginalFilename() + " types are not formatted.");
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
        log.info("gwj : Time taken by CLOVA OCR API = " + (end - start) / 1000);

        return "extractTextFromImageForm";
    }
}
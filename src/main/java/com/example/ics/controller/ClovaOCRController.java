package com.example.ics.controller;

import com.example.ics.dto.clova.ClovaImageType;
import com.example.ics.service.ClovaOCRService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/image")
public class ClovaOCRController {
    private final ClovaOCRService clovaOCRService;

    private static final boolean SYNCHRONOUS = true;

    public ClovaOCRController(ClovaOCRService clovaOCRService) {
        this.clovaOCRService = clovaOCRService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/extractTextFromImage")
    public String uploadForm(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        return "extractTextFromImageForm";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/extractTextFromImage")
    public String handleImage(@RequestPart("imageFiles") List<MultipartFile> imageFiles, Model model) throws IllegalArgumentException {
        StringBuilder result = new StringBuilder();
        for (MultipartFile imageFile : imageFiles) {
            if (ClovaImageType.getExtensionFromMimeType(imageFile.getContentType()) == null) {
                throw new IllegalArgumentException("gwj : " + imageFile.getOriginalFilename() + " types are not formatted.");
            }

            if (SYNCHRONOUS) {
                // 동기
                result.append(clovaOCRService.ocrImageToBlock(clovaOCRService.ocrImage(imageFile)));
            } else {
                // 비동기
                result.append(clovaOCRService.ocrImageToNonBlock(clovaOCRService.ocrImage(imageFile)));
            }
        }
        model.addAttribute("ocrResults", result.toString());

        return "extractTextFromImageForm";
    }
}
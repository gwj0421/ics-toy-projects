package com.example.ics.controller;

import com.example.ics.dto.chatgpt.ChatGPTResponse;
import com.example.ics.service.ChatGPTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/text")
@Slf4j
public class ChatGPTController {
    private final ChatGPTService chatGPTService;
    private static final boolean SYNCHRONOUS = true;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/summarizeText")
    public String sumaryForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("textContent", "");
        model.addAttribute("summaryContent", null);
        return "summarizeTextForm";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/summarizeText")
    public String summaryContent(@RequestParam("textContent") String textContent, Model model) {
        model.addAttribute("textContent", textContent);
        Mono<ChatGPTResponse> summaryContentMono = chatGPTService.summaryContent(textContent);

        if (SYNCHRONOUS) {
            // 동기
            ChatGPTResponse contet = summaryContentMono.block();
            if (contet != null) {
                model.addAttribute("summaryResult", contet.getChoices().get(0).getMessage().getContent());
            }

        } else {
            // 비동기
            summaryContentMono.subscribe(
                    summaryResult -> {
                        if (summaryResult != null) {
                            model.addAttribute("summaryResult", summaryResult.getChoices().get(0).getMessage().getContent());
                        }
                    }
            );
        }

        return "summarizeTextForm";
    }
}

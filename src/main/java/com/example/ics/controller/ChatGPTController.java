package com.example.ics.controller;

import com.example.ics.dto.chatGPT.ChatGPTResponse;
import com.example.ics.service.ChatGPTService;
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

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @GetMapping("/summarizeText")
    public String sumaryForm(Model model) {
        model.addAttribute("textContent", "");
        model.addAttribute("summaryContent", null);
        return "summarizeTextForm";
    }

    @PostMapping("/summarizeText")
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
        return "summarizeTextForm";
    }
}

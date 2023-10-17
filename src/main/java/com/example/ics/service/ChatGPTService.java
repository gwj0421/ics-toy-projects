package com.example.ics.service;

import com.example.ics.dto.chatgpt.ChatGPTContentType;
import com.example.ics.dto.chatgpt.ChatGPTRequest;
import com.example.ics.dto.chatgpt.ChatGPTResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class ChatGPTService {
    private WebClient webClient;

    @Value("${gpt.content-type}")
    private String gptContentType;
    @Value("${gpt.key}")
    private String gptKey;
    @Value("${gpt.model}")
    private String model;

    private static final int MOD = 1;

    public ChatGPTService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ChatGPTResponse> summaryContent(String contents) {
        List<ChatGPTRequest.Messages> chatBootSetting;
        if (MOD == 1) {
            chatBootSetting = List.of(new ChatGPTRequest.Messages("user", "Hi"));
        } else if (MOD == 2) {
            chatBootSetting = List.of(new ChatGPTRequest.Messages("user", String.format("\"%s\"과 같은 내용을 5줄 이내로 한글로 요약해주세요.", contents)));
        }

        ChatGPTRequest chatGPTRequest = new ChatGPTRequest(model, chatBootSetting);
        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .headers(httpHeaders -> {
                    httpHeaders.add("Content-Type", ChatGPTContentType.getContentTypeAsDefault(gptContentType));
                    httpHeaders.add("Authorization", "Bearer " + gptKey);
                })
                .bodyValue(chatGPTRequest)
                .retrieve()
                .bodyToMono(ChatGPTResponse.class)
                .onErrorResume(throwable -> {
                    log.error("gwj : chatGPT error = " + throwable.getMessage());
                    return Mono.empty();
                });
    }
}

package com.example.ics.service;

import com.example.ics.dto.ChatGPTMessage;
import com.example.ics.dto.ChatGPTRequest;
import com.example.ics.dto.ChatGPTResponse;
import com.example.ics.dto.ClovaOCRResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class ChatGPTService {
    private WebClient webClient;
    private ObjectMapper objectMapper;


    @Value("${gpt.content-type}")
    private String gptContentType;

    @Value("${gpt.key}")
    private String gptKey;

    @Value("${gpt.model}")
    private String model;

    public ChatGPTService(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public Mono<String> summaryContent(String contents) {
        List<ChatGPTMessage> smMode = List.of(new ChatGPTMessage("user", String.format("\"%s\"과 같은 내용을 5줄 이내로 한글로 요약해주세요.", contents)));
        String requestBody = convertJson2Str(new ChatGPTRequest(model, smMode));
        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .headers(httpHeaders -> {
                    httpHeaders.add("Content-Type", gptContentType);
                    httpHeaders.add("Authorization", gptKey);
                })
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(responseBody -> {
                    ChatGPTResponse chatGPTResponse = convertStr2Json(responseBody);
                    return Mono.just(chatGPTResponse.getChoices().get(0).getMessage().getContent());
                })
                .onErrorResume(throwable -> {
                    log.error(throwable.getMessage());
                    return Mono.empty();
                });
    }

    private String convertJson2Str(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing Json2Str");
        }

    }

    private ChatGPTResponse convertStr2Json(String jsonStr){
        try {
            return objectMapper.readValue(jsonStr, ChatGPTResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing Str2Json");
        }

    }
}

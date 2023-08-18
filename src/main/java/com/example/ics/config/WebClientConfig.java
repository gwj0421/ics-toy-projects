package com.example.ics.config;

import com.example.ics.dto.ClovaOCRResponse;
import com.example.ics.dto.ClovaOcrMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Configuration
public class WebClientConfig {
    @Value("${clova.ocr.api.url}")
    String clovaOcrUrl;

    @Value("${clova.ocr.version}")
    String clovaOcrVersion;

    @Value("${clova.ocr.lang}")
    String clovaOcrLang;


    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public ClovaOcrMessage clovaOcrMessage() {
        return new ClovaOcrMessage("V2");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ClovaOCRResponse clovaOCRResponse() {
        return new ClovaOCRResponse();
    }
}

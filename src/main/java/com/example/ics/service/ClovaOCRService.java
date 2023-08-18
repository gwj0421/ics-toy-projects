package com.example.ics.service;


import com.example.ics.dto.ClovaOCRResponse;
import com.example.ics.dto.ClovaOcrMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class ClovaOCRService {
    private final WebClient webClient;
    private final ClovaOcrMessage clovaOcrMessage;
    private final ObjectMapper objectMapper;

    @Value("${clova.ocr.api.url}")
    private String clovaOcrApiUrl;

    @Value("${clova.ocr.api.secret.key}")
    private String clovaOcrApiSecretKey;

    @Value("${clova.ocr.contentType}")
    private String clovaOcrContentType;

    private MultipartBodyBuilder makeBody(MultipartFile imageFile) throws IOException {
        String[] imgName = imageFile.getOriginalFilename().split("\\.");
        List<Map<String, String>> images = List.of(Map.of("format", imgName[1], "name", imgName[0]));

        clovaOcrMessage.setImages(images, UUID.randomUUID().toString(), (int) System.currentTimeMillis());

        String jsonStr = convertJson2Str();

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("message", jsonStr);
        builder.part("file", imageFile.getResource());
        return builder;
    }

    private String convertJson2Str() throws JsonProcessingException {
        return objectMapper.writeValueAsString(clovaOcrMessage);
    }

    private ClovaOCRResponse convertStr2Json(String jsonStr) throws JsonProcessingException {
        return objectMapper.readValue(jsonStr, ClovaOCRResponse.class);
    }

    public String ocrImage(MultipartFile imageFile) throws IOException {
        Mono<String> responseBody = webClient.post()
                .uri(clovaOcrApiUrl)
                .headers(httpHeaders -> {
                    httpHeaders.add("X-OCR-SECRET", clovaOcrApiSecretKey);
                    httpHeaders.add("Content-Type", clovaOcrContentType);
                })
                .body(BodyInserters.fromMultipartData(makeBody(imageFile).build()))
                .retrieve()
                .bodyToMono(String.class);


        ClovaOCRResponse response = convertStr2Json(responseBody.block());

        String result = "";
        for (ClovaOCRResponse.ImageRecognitionResults image : response.getImages()) {
            for (ClovaOCRResponse.ImageField field : image.getFields()) {
                // 추가로 정확도에 따라 다르게 출력 기능 추가 요망
                result += field.getInferText() + " ";
            }
        }

        return result;
    }
}


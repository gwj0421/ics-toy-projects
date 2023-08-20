package com.example.ics.service;


import com.example.ics.dto.Clova.ClovaContentType;
import com.example.ics.dto.Clova.ClovaOCRResponse;
import com.example.ics.dto.Clova.ClovaOcrMessage;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class ClovaOCRService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${clova.ocr.api.url}")
    private String clovaOcrApiUrl;

    @Value("${clova.ocr.api.secret.key}")
    private String clovaOcrApiSecretKey;

    @Value("${clova.ocr.contentType}")
    private String clovaOcrContentType;

    @Value("${clova.ocr.version}")
    private String version;

    private MultipartBodyBuilder makeBody(MultipartFile imageFile) {
        String imgName = imageFile.getOriginalFilename();
        List<Map<String, String>> images = List.of(Map.of("format", imgName.substring(imgName.lastIndexOf(".") + 1), "name", imgName.substring(0, imgName.lastIndexOf("."))));

        ClovaOcrMessage clovaOcrMessage = new ClovaOcrMessage(images, UUID.randomUUID().toString(), version, (int) System.currentTimeMillis());

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("message", convertJson2Str(clovaOcrMessage));
        builder.part("file", imageFile.getResource());
        return builder;
    }

    private String convertJson2Str(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("gwj : convertJson2Str Error, " + e);
        }

    }

    public Mono<ClovaOCRResponse> ocrImage(MultipartFile imageFile) {
        return webClient.post()
                .uri(clovaOcrApiUrl)
                .headers(httpHeaders -> {
                    httpHeaders.add("X-OCR-SECRET", clovaOcrApiSecretKey);
                    httpHeaders.add("Content-Type", ClovaContentType.getContentTypeAsDefault(clovaOcrContentType));
                })
                .body(BodyInserters.fromMultipartData(makeBody(imageFile).build()))
                .retrieve()
                .bodyToMono(ClovaOCRResponse.class)
                .onErrorResume(throwable -> {
                    log.error("gwj : " + throwable.getMessage());
                    return Mono.empty();
                });
    }

    public String ocrImageToBlock(Mono<ClovaOCRResponse> responseMono) {
        List<String> eachPageResult = new ArrayList<>();
        responseMono.block().getImages().get(0).getFields().forEach(field -> eachPageResult.add(field.getInferText()));
        return String.join(" ", eachPageResult);
    }

    public String ocrImageToNonBlock(Mono<ClovaOCRResponse> responseMono) {
        List<String> eachPageResult = new ArrayList<>();
        responseMono.subscribe(response -> response.getImages().get(0).getFields().forEach(field -> eachPageResult.add(field.getInferText())));
        return String.join(" ", eachPageResult);
    }
}


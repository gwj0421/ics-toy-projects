package com.example.ics;

import com.example.ics.dto.Clova.ClovaContentType;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

@SpringBootTest
@Slf4j
class IcsApplicationTests {

    @Value("${clova.ocr.contentType}")
    String clovaOcrContentType;

    @Test
    void contextLoads() {
    }

}

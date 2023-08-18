package com.example.ics;

import com.example.ics.dto.ClovaOCRResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

@SpringBootTest
@Slf4j
class IcsApplicationTests {

    @Test
    void contextLoads() throws JsonProcessingException {
        Mono<String> test = Mono.just("test....");
        test.subscribe(System.out::println);
    }

}

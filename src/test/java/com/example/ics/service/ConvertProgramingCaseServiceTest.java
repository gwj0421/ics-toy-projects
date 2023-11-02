package com.example.ics.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.ics.dto.programing_case.ProgramingCaseType.CAMEL;
import static com.example.ics.dto.programing_case.ProgramingCaseType.ORIGINAL;

class ConvertProgramingCaseServiceTest {
    private ConvertProgramingCaseService convertService = new ConvertProgramingCaseService();
    private String original;
    private String camel;
    private String kebab;
    private String snake_up;
    private String snake_down;
    private String pascal;

    @BeforeEach
    void setup() {
        // given
        original = "My visitor count\nMy visitor count";
        camel = "myVisitorCount\nmyVisitorCount";
        kebab = "my-visitor-count\nmy-visitor-count";
        snake_up = "MY_VISITOR_COUNT\nMY_VISITOR_COUNT";
        snake_down = "my_visitor_count\nmy_visitor_count";
        pascal = "MyVisitorCount\nMyVisitorCount";
    }

    @Test
    void testConvert() {
        // when
        String result = convertService.convert(ORIGINAL, CAMEL, original);

        // then
        Assertions.assertThat(result).isEqualTo(camel);
    }

}
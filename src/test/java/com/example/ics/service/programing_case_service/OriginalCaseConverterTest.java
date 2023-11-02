package com.example.ics.service.programing_case_service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OriginalCaseConverterTest {
    private OriginalCaseConverter originalCaseConverter = new OriginalCaseConverter();
    private String original;
    private String camel;
    private String kebab;
    private String upSnake;
    private String lowSnake;
    private String pascal;

    @BeforeEach
    void setup() {
        // given
        original = "My visitor count";
        camel = "myVisitorCount";
        kebab = "my-visitor-count";
        upSnake = "MY_VISITOR_COUNT";
        lowSnake = "my_visitor_count";
        pascal = "MyVisitorCount";
    }

    @Test
    void convertOriginal2Camel() {
        // when
        String result = originalCaseConverter.convertCamel(original);

        // then
        Assertions.assertThat(result).isEqualTo(camel);
    }

    @Test
    void convertOriginal2Kebab() {
        // when
        String result = originalCaseConverter.convertKebab(original);

        // then
        Assertions.assertThat(result).isEqualTo(kebab);
    }

    @Test
    void convertOriginal2Snake() {
        // when
        String snakeUpResult = originalCaseConverter.convertSnake(original,true);
        String snakeDownResult = originalCaseConverter.convertSnake(original,false);

        // then
        Assertions.assertThat(snakeUpResult).isEqualTo(upSnake);
        Assertions.assertThat(snakeDownResult).isEqualTo(lowSnake);
    }

    @Test
    void convertOriginal2Pascal() {
        // when
        String pascalResult = originalCaseConverter.convertPascal(original);

        // then
        Assertions.assertThat(pascalResult).isEqualTo(pascal);
    }
}
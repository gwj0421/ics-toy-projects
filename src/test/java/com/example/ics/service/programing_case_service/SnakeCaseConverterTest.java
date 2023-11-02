package com.example.ics.service.programing_case_service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnakeCaseConverterTest {
    private SnakeCaseConverter upSnakeCaseConverter = new SnakeCaseConverter(true);
    private SnakeCaseConverter lowSnakeCaseConverter = new SnakeCaseConverter(false);
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
    void convertSnakeCase2Original() {
        // when
        String originalResultByUp = upSnakeCaseConverter.convertOriginal(upSnake);
        String originalResultByDown = lowSnakeCaseConverter.convertOriginal(lowSnake);

        // then
        Assertions.assertThat(originalResultByUp).isEqualTo(original);
        Assertions.assertThat(originalResultByDown).isEqualTo(original);
    }

    @Test
    void convertSnakeCase2Camel() {
        // when
        String camelResultByUp = upSnakeCaseConverter.convertCamel(upSnake);
        String camelResultByDown = lowSnakeCaseConverter.convertCamel(lowSnake);

        // then
        Assertions.assertThat(camelResultByUp).isEqualTo(camel);
        Assertions.assertThat(camelResultByDown).isEqualTo(camel);
    }

    @Test
    void convertSnakeCase2Kebab() {
        // when
        String kebabUpResult = upSnakeCaseConverter.convertKebab(upSnake);
        String kebabDownResult = lowSnakeCaseConverter.convertKebab(lowSnake);

        // then
        Assertions.assertThat(kebabUpResult).isEqualTo(kebab);
        Assertions.assertThat(kebabDownResult).isEqualTo(kebab);
    }

    @Test
    void convertSnakeCase2Pascal() {
        // when
        String pascalUpResult = upSnakeCaseConverter.convertPascal(upSnake);
        String pascalDownResult = lowSnakeCaseConverter.convertPascal(lowSnake);

        // then
        Assertions.assertThat(pascalUpResult).isEqualTo(pascal);
        Assertions.assertThat(pascalDownResult).isEqualTo(pascal);
    }
}
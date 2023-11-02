package com.example.ics.service.programing_case_converter;

import com.example.ics.programing_case_converter.CamelCaseConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CamelCaseConverterTest {
    private CamelCaseConverter camelCaseConverter = new CamelCaseConverter();
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
    void convertCamel2Original() {
        // when
        String originalResult = camelCaseConverter.convertOriginal(camel);

        // then
        Assertions.assertThat(originalResult).isEqualTo(original);
    }

    @Test
    void convertCamel2Camel() {
        // when
    }

    @Test
    void convertCamel2Kebab() {
        // when
        String kebabResult = camelCaseConverter.convertKebab(camel);

        // then
        Assertions.assertThat(kebabResult).isEqualTo(kebab);
    }

    @Test
    void convertCamel2Snake() {
        // when
        String snakeUpResult = camelCaseConverter.convertSnake(camel,true);
        String snakeDownResult = camelCaseConverter.convertSnake(camel,false);

        // then
        Assertions.assertThat(snakeUpResult).isEqualTo(upSnake);
        Assertions.assertThat(snakeDownResult).isEqualTo(lowSnake);
    }

    @Test
    void convertCamel2Pascal() {
        // when
        String pascalResult = camelCaseConverter.convertPascal(camel);

        // then
        Assertions.assertThat(pascalResult).isEqualTo(pascal);
    }

}
package com.example.ics.service.programing_case_converter;

import com.example.ics.programing_case_converter.KebabCaseConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KebabCaseConverterTest {
    private KebabCaseConverter kebabCaseConverter = new KebabCaseConverter();
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
    void convertKebab2Original() {
        // when
        String originalResult = kebabCaseConverter.convertOriginal(kebab);

        // then
        Assertions.assertThat(originalResult).isEqualTo(original);
    }

    @Test
    void convertKebab2Camel() {
        // when
        String camelResult = kebabCaseConverter.convertCamel(kebab);

        // then
        Assertions.assertThat(camelResult).isEqualTo(camel);
    }

    @Test
    void convertKebab2Snake() {
        // when
        String snakeUpResult = kebabCaseConverter.convertSnake(kebab, true);
        String snakeDownResult = kebabCaseConverter.convertSnake(kebab, false);

        // then
        Assertions.assertThat(snakeUpResult).isEqualTo(upSnake);
        Assertions.assertThat(snakeDownResult).isEqualTo(lowSnake);
    }

    @Test
    void convertKebab2Pascal() {
        // when
        String pascalResult = kebabCaseConverter.convertPascal(kebab);

        // then
        Assertions.assertThat(pascalResult).isEqualTo(pascal);
    }

}
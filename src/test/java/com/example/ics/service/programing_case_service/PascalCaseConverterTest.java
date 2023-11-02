package com.example.ics.service.programing_case_service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PascalCaseConverterTest {
    private PascalCaseConverter pascalCaseConverter = new PascalCaseConverter();
    private String original;
    private String camel;
    private String kebab;
    private String upSnake;
    private String downSnake;
    private String pascal;

    @BeforeEach
    void setup() {
        // given
        original = "My visitor count";
        camel = "myVisitorCount";
        kebab = "my-visitor-count";
        upSnake = "MY_VISITOR_COUNT";
        downSnake = "my_visitor_count";
        pascal = "MyVisitorCount";
    }

    @Test
    void convertPascal2Original() {
        // when
        String originalResult = pascalCaseConverter.convertOriginal(pascal);

        // then
        Assertions.assertThat(originalResult).isEqualTo(original);
    }

    @Test
    void convertPascal2Camel() {
        // when
        String camelResult = pascalCaseConverter.convertCamel(pascal);

        // then
        Assertions.assertThat(camelResult).isEqualTo(camel);
    }

    @Test
    void convertPascal2Kebab() {
        // when
        String kebabResult = pascalCaseConverter.convertKebab(pascal);

        // then
        Assertions.assertThat(kebabResult).isEqualTo(kebab);
    }

    @Test
    void convertPascal2Snake() {
        // when
        String UpSnakeResult = pascalCaseConverter.convertSnake(pascal,true);
        String DownSnakeResult = pascalCaseConverter.convertSnake(pascal,false);

        // then
        Assertions.assertThat(UpSnakeResult).isEqualTo(upSnake);
        Assertions.assertThat(DownSnakeResult).isEqualTo(downSnake);
    }

}
package com.example.ics.programing_case_converter;

import com.example.ics.dto.programing_case.ProgramingCaseType;

import java.util.StringJoiner;

public abstract class ProgramingCase {
    public static final StringBuilder stringBuilder = new StringBuilder();

    public String convert(ProgramingCaseType caseType, String contents) {
        interface ConverterEngine {
            String apply(String content);
        }
        ConverterEngine converter;
        switch (caseType) {
            case ORIGINAL:
                converter = this::convertOriginal;
                break;
            case CAMEL:
                converter = this::convertCamel;
                break;
            case KEBAB:
                converter = this::convertKebab;
                break;
            case SNAKE_UP:
                converter = content -> convertSnake(content, true);
                break;
            case SNAKE_DOWN:
                converter = content -> convertSnake(content, false);
                break;
            case PASCAL:
                converter = this::convertPascal;
                break;
            default:
                throw new IllegalArgumentException("잘못된 케이스 타입 입력");
        }

        StringJoiner result = new StringJoiner("\n");
        for (String content : contents.split("\n")) {
            result.add(converter.apply(content));
        }
        return result.toString();
    }

    public String convertOriginal(String content) {
        return content;
    }

    public String convertCamel(String content) {
        return content;
    }

    public String convertKebab(String content) {
        return content;
    }

    public String convertSnake(String content, boolean isUp) {
        if (isUp) {
            return content.toUpperCase();
        }
        return content.toLowerCase();
    }

    public String convertPascal(String content) {
        return content;
    }
}

package com.example.ics.dto.programing_case;

public enum ProgramingCaseType {
    ORIGINAL,
    CAMEL,
    KEBAB,
    SNAKE_UP,
    SNAKE_DOWN,
    PASCAL;
    public static ProgramingCaseType getCase(String content) {
        return valueOf(content.toUpperCase());
    }
}

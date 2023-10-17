package com.example.ics.dto.chatgpt;

import lombok.Getter;

@Getter
public enum ChatGPTContentType {
    JSON("JSON", "application/json");

    private final String format;
    private final String contentType;

    ChatGPTContentType(String format, String contentType) {
        this.format = format;
        this.contentType = contentType;
    }


    public static String getContentTypeAsDefault(String defaultContentType) {
        try {
            return valueOf(defaultContentType.toUpperCase()).getContentType();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("gwj : chatGPT 설정오류, 없는 컨텐츠 타입입니다");
        }
    }
}

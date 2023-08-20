package com.example.ics.dto.chatGPT;

public enum ChatGPTContentType {
    JSON("JSON", "application/json");

    private final String format;
    private final String contentType;

    ChatGPTContentType(String format, String contentType) {
        this.format = format;
        this.contentType = contentType;
    }


    public static String getContentTypeAsDefault(String defaultContentType) {
        for (ChatGPTContentType value : values()) {
            if (value.format.equalsIgnoreCase(defaultContentType)) {
                return value.contentType;
            }
        }
        throw new RuntimeException("gwj : " + defaultContentType + " type is not format");
    }
}

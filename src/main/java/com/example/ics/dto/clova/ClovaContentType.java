package com.example.ics.dto.clova;

import lombok.Getter;

@Getter
public enum ClovaContentType {
    JSON("JSON", "application/json"),
    MULTIPART("MULTIPART","multipart/form-data");

    private final String format;
    private final String contentType;

    ClovaContentType(String format, String contentType) {
        this.format = format;
        this.contentType = contentType;
    }

    public static String getContentTypeAsDefault(String defaultContentType) {
        try {
            return valueOf(defaultContentType.toUpperCase()).getContentType();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("gwj : " + defaultContentType + " type is not format");
        }
    }
}

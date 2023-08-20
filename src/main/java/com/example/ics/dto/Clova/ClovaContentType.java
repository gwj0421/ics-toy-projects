package com.example.ics.dto.Clova;

public enum ClovaContentType {
    JSON("JSON", "application/json"),
    MULTIPART("MULTIPART","multipart/form-data");

    public String format;
    public String contentType;

    ClovaContentType(String format, String contentType) {
        this.format = format;
        this.contentType = contentType;
    }

    public static String getContentTypeAsDefault(String defaultContentType) {
        for (ClovaContentType value : values()) {
            if (value.format.equalsIgnoreCase(defaultContentType)) {
                return value.contentType;
            }
        }
        throw new RuntimeException("gwj : " + defaultContentType + " type is not format");
    }
}

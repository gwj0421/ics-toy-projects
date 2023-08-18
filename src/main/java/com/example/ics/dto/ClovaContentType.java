package com.example.ics.dto;

public enum ClovaContentType {
    JSON("application/json"),
    MULTIPART("multipart/form-data");

    public String contentType;

    ClovaContentType(String contentType) {
        this.contentType = contentType;
    }
}

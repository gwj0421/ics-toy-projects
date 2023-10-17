package com.example.ics.dto.clova;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ClovaOcrMessage {
    private List<Map<String, String>> images;
    private String requestId;
    private String version;
    private Integer timestamp;

    public ClovaOcrMessage(List<Map<String, String>> images, String requestId, String version, Integer timestamp) {
        this.images = images;
        this.requestId = requestId;
        this.version = version;
        this.timestamp = timestamp;
    }
}

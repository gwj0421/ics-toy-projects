package com.example.ics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClovaOcrMessage {
    @JsonProperty
    private List<Map<String, String>> images;
    @JsonProperty
    private String requestId;
    @JsonProperty
    private String version;
    @JsonProperty
    private Integer timestamp;

    public ClovaOcrMessage(String version) {
        this.version = version;
    }

    public void setImages(List<Map<String, String>> images,String requestId,Integer timestamp) {
        this.images = images;
        this.requestId = requestId;
        this.timestamp = timestamp;
    }
}

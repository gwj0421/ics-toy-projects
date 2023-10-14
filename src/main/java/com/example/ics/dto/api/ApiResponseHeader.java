package com.example.ics.dto.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ApiResponseHeader {
    private int code;
    private String message;

    @JsonCreator
    public ApiResponseHeader(@JsonProperty("code") int code, @JsonProperty("message") String message) {
        this.code = code;
        this.message = message;
    }
}

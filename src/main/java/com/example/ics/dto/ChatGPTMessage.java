package com.example.ics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatGPTMessage {
    @JsonProperty
    private String role;
    @JsonProperty
    private String content;

    public ChatGPTMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
}

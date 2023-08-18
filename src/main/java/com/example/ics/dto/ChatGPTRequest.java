package com.example.ics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChatGPTRequest {
    @JsonProperty
    private String model;

    @JsonProperty
    private List<ChatGPTMessage> messages;

    public ChatGPTRequest(String model, List<ChatGPTMessage> messages) {
        this.model = model;
        this.messages = messages;
    }
}

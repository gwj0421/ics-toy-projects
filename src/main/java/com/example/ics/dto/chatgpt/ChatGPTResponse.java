package com.example.ics.dto.chatgpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatGPTResponse {
    private String id;
    private String object;
    private Integer created;
    private String model;
    private UseStatistics usage;
    private List<Choices> choices;

    @Getter
    public static class UseStatistics {
        @JsonProperty("promt_tokens")
        private Integer promptTokens;
        @JsonProperty("completion_tokens")
        private Integer completionTokens;
        @JsonProperty("total_tokens")
        private Integer totalTokens;
    }

    @Getter
    public static class Choices {
        private Integer index;
        private Message message;
        @JsonProperty("finish_reason")
        private String finishReason;

    }

    @Getter
    public static class Message {
        private String role;
        private String content;

    }
}
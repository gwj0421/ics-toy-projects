package com.example.ics.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ChatGPTResponse {
    private String id;
    private String object;
    private Integer created;
    private String model;
    private List<Choice> choices;
    private UseStatistics usage;

    @Getter
    public static class UseStatistics {
        private Integer prompt_tokens;
        private Integer completion_tokens;
        private Integer total_tokens;
    }


    @Getter
    public static class Choice {
        private Integer index;
        private ReceivedMessage message;
        private String finish_reason;
    }

    @Getter
    public static class ReceivedMessage {
        private String role;
        private String content;
    }
}

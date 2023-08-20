package com.example.ics.dto.chatGPT;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
        private Integer prompt_tokens;
        private Integer completion_tokens;
        private Integer total_tokens;
    }

    @Getter
    public static class Choices {
        private Integer index;
        private Message message;
        private String finish_reason;

    }

    @Getter
    public static class Message {
        private String role;
        private String content;

    }
}
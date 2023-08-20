package com.example.ics.dto.chatGPT;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ChatGPTRequest {
    private String model;
    private List<Messages> messages;

    /**
     * 생성된 텍스트의 다양성과 무작위성을 조절하는 변수, 값이 낮을수록 더 일관된 출력이 생성
     * What sampling temperature to use, between 0 and 2.
     * Higher values like 0.8 will make the output more random,
     * while lower values like 0.2 will make it more focused and deterministic.
     */
    private Double temperature;

    /**
     * 생성될 텍스트의 최대 토큰 수를 나타내는 변수, 텍스트의 길이를 제어
     * The total length of input tokens and generated tokens is limited by the model's context length.
     */
    @JsonProperty("max_tokens")
    private Integer maxTokens;

    /**
     * Top-p(누적 확률) 샘플링을 사용하여 생성된 텍스트를 다양하게 만드는 변수, 값이 낮을수록 더 일관된 출력이 생성
     * 0.1 means only the tokens comprising the top 10% probability mass are considered.
     */
    @JsonProperty("top_p")
    private Double topP;

    /**
     * 높은 빈도 단어를 피하기 위한 패널티를 설정하는 변수, 값이 높을수록 일반적인 단어의 사용이 감소
     * Number between -2.0 and 2.0.
     * Positive values penalize new tokens based on whether they appear in the text so far,
     * increasing the model's likelihood to talk about new topics.
     */
    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty;

    /**
     * 새로운 단어를 추가할 때의 패널티를 설정하는 변수, 값이 높을수록 새로운 단어의 사용이 감소
     * Number between -2.0 and 2.0.
     * Positive values penalize new tokens based on their existing frequency in the text so far,
     * decreasing the model's likelihood to repeat the same line verbatim.
     */
    @JsonProperty("presence_penalty")
    private Double presencePenalty;

    @Builder
    public ChatGPTRequest(String model, List<Messages> messages) {
        this.model = model;
        this.messages = messages;
    }

    @Builder
    public ChatGPTRequest(String model, List<Messages> messages, Double temperature, Integer maxTokens, Double topP, Double frequencyPenalty, Double presencePenalty) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
        this.topP = topP;
        this.frequencyPenalty = frequencyPenalty;
        this.presencePenalty = presencePenalty;
    }

    @Getter
    public static class Messages {
        /**
         * 역할
         * The role of the messages author.
         * One of system, user, assistant, or function.
         */
        private String role;
        /**
         * 보낼 내용
         * The contents of the message.
         * content is required for all messages,
         * and may be null for assistant messages with function calls.
         */
        private String content;

        public Messages(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
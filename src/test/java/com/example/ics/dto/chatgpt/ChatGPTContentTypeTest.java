package com.example.ics.dto.chatgpt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ChatGPTContentTypeTest {

    @Test
    void getContentTypeAsDefault() {
        // given
        String contentType = "json";

        // when
        String result = ChatGPTContentType.getContentTypeAsDefault(contentType);

        // then
        Assertions.assertThat(result).isEqualTo("application/json");

    }
}
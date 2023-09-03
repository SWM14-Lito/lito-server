package com.lito.core.chat.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("Chat")
class ChatTest {

    @Nested
    @DisplayName("of 메서드는")
    class of{

        @Nested
        @DisplayName("userId, problemId, question, answer를 가지고")
        class with_userId_problemId_question_answer{

            Long userId = 1L;
            Long problemId = 1L;
            String question = "질문";
            String answer = "답변";

            @Test
            @DisplayName("Chat을 생성한다.")
            void it_creates_chat() throws Exception{

                Chat chat = Chat.of(userId, problemId, question, answer);

                assertThat(chat)
                        .extracting("userId","problemId","question","answer")
                        .contains(1L,1L,"질문","답변");

            }
        }
    }
}
package com.lito.core.problem.domain;

import com.lito.core.problem.domain.Faq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("FAQ")
class FaqTest {

    @Nested
    @DisplayName("createFaq 메서드는")
    class create_faq{

        @Nested
        @DisplayName("question, answer를 가지고")
        class with_question_answer{

            String question = "질문";
            String answer = "답변";

            @Test
            @DisplayName("faq를 생성한다.")
            void it_creates_faq() {

                Faq faq = Faq.createFaq(question, answer);

                assertThat(faq.getQuestion()).isEqualTo(question);
                assertThat(faq.getAnswer()).isEqualTo(answer);
            }
        }

    }
}
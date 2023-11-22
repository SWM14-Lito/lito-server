package com.lito.core.problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
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

    @Nested
    @DisplayName("setProblem 메서드는")
    class set_problem{

        @Nested
        @DisplayName("problem을 가지고")
        class with_problem{
            Subject subject = Subject.builder()
                    .subjectName("운영체제")
                    .build();
            SubjectCategory subjectCategory = SubjectCategory.builder()
                    .subjectCategoryName("프로세스관리")
                    .build();
            String question = "문맥 전환이 무엇인가?";
            String answer = "CPU가 이전 상태의 프로세스를 PCB에 보관하고, 또 다른 프로세스를 PCB에서 읽어 레지스터에 적재하는 과정";
            String keyword = "PCB";
            Problem problem = Problem.createProblem(subject, subjectCategory, question, answer, keyword);
            Faq faq = Faq.createFaq(question, answer);

            @Test
            @DisplayName("faq엔티티에 problem 연관관계를 설정한다.")
            void it_sets_problem() throws Exception{

                faq.setProblem(problem);

                assertThat(faq.getProblem())
                        .extracting("question","answer","keyword")
                        .contains(question, answer, keyword);
            }
        }
    }
}
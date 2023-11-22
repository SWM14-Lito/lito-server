package com.lito.core.problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Problem")
@ActiveProfiles("test")
class ProblemTest {

    @Nested
    @DisplayName("createProblem 메서드는")
    class create_problem{

        @Nested
        @DisplayName("subject, subjectCategory, question, answer, keyword, faqs를 가지고")
        class with_subject_subject_category_question_answer_keyword_faqs{


                    Subject subject = Subject.builder()
                            .subjectName("운영체제")
                            .build();
                    SubjectCategory subjectCategory = SubjectCategory.builder()
                            .subjectCategoryName("프로세스관리")
                            .build();
                    String question = "문맥 전환이 무엇인가?";
                    String answer = "CPU가 이전 상태의 프로세스를 PCB에 보관하고, 또 다른 프로세스를 PCB에서 읽어 레지스터에 적재하는 과정";
                    String keyword = "PCB";

            @Test
            @DisplayName("problem을 생성한다.")
            void it_creates_problem() throws Exception{

                Problem problem = Problem.createProblem(subject, subjectCategory, question, answer, keyword);

                assertAll(
                        () -> assertThat(problem.getSubject().getSubjectName()).isEqualTo(subject.getSubjectName()),
                        () -> assertThat(problem.getSubjectCategory().getSubjectCategoryName()).isEqualTo(subjectCategory.getSubjectCategoryName()),
                        () -> assertThat(problem.getQuestion()).isEqualTo(question),
                        () -> assertThat(problem.getAnswer()).isEqualTo(answer),
                        () -> assertThat(problem.getKeyword()).isEqualTo(keyword)
                );
            }
        }
    }

}
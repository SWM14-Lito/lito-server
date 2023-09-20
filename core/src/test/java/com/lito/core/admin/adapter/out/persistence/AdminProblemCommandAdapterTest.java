package com.lito.core.admin.adapter.out.persistence;

import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.Subject;
import com.lito.core.problem.domain.SubjectCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatCode;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("AdminProblemCommandAdapter")
class AdminProblemCommandAdapterTest {

    @Autowired
    AdminProblemCommandAdapter adminProblemCommandAdapter;

    @Autowired
    AdminProblemRepository adminProblemRepository;

    @Nested
    @DisplayName("save 메서드는")
    class save{

        @Nested
        @DisplayName("problem을 가지고")
        class with_problem{

            Problem problem = Problem.builder()
                    .subject(Subject.builder()
                            .subjectName("운영체제")
                            .build())
                    .subjectCategory(SubjectCategory.builder()
                            .subjectCategoryName("프로세스관리")
                            .build())
                    .question("문맥 전환이 무엇인가?")
                    .answer("CPU가 이전 상태의 프로세스를 PCB에 보관하고, 또 다른 프로세스를 PCB에서 읽어 레지스터에 적재하는 과정")
                    .keyword("PCB")
                    .build();
            @Test
            @DisplayName("db에 저장한다.")
            void it_saves_problem() throws Exception{

                assertThatCode(() -> adminProblemCommandAdapter.save(problem))
                        .doesNotThrowAnyException();
            }
        }
    }
}
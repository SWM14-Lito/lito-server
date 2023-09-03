package com.lito.core.problem.domain;

import com.lito.core.problem.adapter.out.persistence.ProblemRepository;
import com.lito.core.problem.domain.*;
import com.lito.core.problem.domain.enums.ProblemStatus;
import com.lito.core.user.adapter.out.persistence.UserRepository;
import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("ProblemUser")
class ProblemUserTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProblemRepository problemRepository;


    User user = User.builder()
            .oauthId("kakaoId")
            .email("test@test.com")
            .provider(Provider.KAKAO)
            .build();

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
            .faqs(List.of(Faq.createFaq("PCB란?", "PCB 는 특정프로세스에 대한 중요한 정보를 저장하고 있는 운영체제의 자료구조이다")))
            .build();
    ProblemUser problemUser = ProblemUser.createProblemUser(problem, user);
    ProblemUser problemUser2 = ProblemUser.builder()
            .problemStatus(ProblemStatus.NOT_SEEN)
            .build();

    @Nested
    @DisplayName("createProblemUser 메서드는")
    class create_problem_user{

        @Nested
        @DisplayName("problem, user를 가지고")
        class with_problem_user{

            @Test
            @DisplayName("ProblemUser를 생성한다.")
            void it_creates_problem_user() {


                assertThat(problemUser.getProblemStatus()).isEqualTo(ProblemStatus.PROCESS);
                assertThat(problemUser.getUnsolvedCnt()).isEqualTo(0);
            }
        }
    }

    @Nested
    @DisplayName("changeStatus 메서드는")
    class change_status{

        @Nested
        @DisplayName("problemStatus를 가지고")
        class with_problem_status{

            @Test
            @DisplayName("PROCESS 상태를 COMPLETE상태로 변경한다.")
            void it_changes_process_to_complete() {

                problemUser.changeStatus(problemUser.getProblemStatus());

                assertThat(problemUser.getProblemStatus()).isEqualTo(ProblemStatus.COMPLETE);
            }
        }

        @Nested
        @DisplayName("만약 problemStatus가 PROCESS가 아니라면")
        class with_problem_status_not_process{

            @Test
            @DisplayName("COMPLETE로 변경되지 않는다.")
            void it_does_not_change_to_complete() throws Exception{

                problemUser2.changeStatus(problemUser2.getProblemStatus());

                assertThat(problemUser2.getProblemStatus()).isNotEqualTo(ProblemStatus.COMPLETE);
            }
        }
    }

    @Nested
    @DisplayName("addUnsolved 메서드는")
    class add_unsolved{

        @Test
        @DisplayName("unsolvedCnt를 1 증가 시킨다.")
        void it_increases_unsolved_cnt() throws Exception{

            problemUser.addUnsolved();

            assertThat(problemUser.getUnsolvedCnt()).isEqualTo(1);
        }
    }
}
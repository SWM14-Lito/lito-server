package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.adapter.out.persistence.ProblemCommandAdapter;
import com.lito.core.problem.adapter.out.persistence.ProblemRepository;
import com.lito.core.problem.domain.*;
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

import static org.assertj.core.api.Assertions.assertThatCode;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("ProblemCommandAdapter")
class ProblemCommandAdapterTest {

    @Autowired
    ProblemCommandAdapter problemCommandAdapter;

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
            .build();

    @BeforeEach
    void setUp(){
        userRepository.save(user);
        problemRepository.save(problem);
    }

    @Nested
    @DisplayName("favorite을 저장하는 save 메서드는")
    class save_favorite{

        @Nested
        @DisplayName("favorite을 가지고")
        class with_favorite{

            @Test
            @DisplayName("favorite을 db에 저장한다.")
            void it_saves_favorite() {

                assertThatCode(() -> problemCommandAdapter.save(Favorite.createFavorite(user, problem)))
                        .doesNotThrowAnyException();
            }
        }
    }

    @Nested
    @DisplayName("problemUser를 저장하는 save 메서드는")
    class save_problem_user{

        @Nested
        @DisplayName("problem, user를 가지고")
        class with_problem_user{

            @Test
            @DisplayName("problem user를 db에 저장한다.")
            void it_saves_problem_user() {

                assertThatCode(() -> problemCommandAdapter.save(ProblemUser.createProblemUser(problem, user)))
                        .doesNotThrowAnyException();
            }
        }
    }
}
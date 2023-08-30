package com.lito.core.problem.domain;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("Favorite")
class FavoriteTest {

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

    Long problemId;
    Long userId;

    @BeforeEach
    void setUp(){
        problemId = problemRepository.save(problem).getId();
        userId = userRepository.save(user).getId();
    }

    @Nested
    @DisplayName("createFavorite")
    class create_favorite{

        @Nested
        @DisplayName("user, problem을 가지고")
        class with_user_problem{

            @Test
            @DisplayName("favorite을 생성한다.")
            void it_creates_favorite() {

                Favorite favorite = Favorite.createFavorite(user, problem);

                assertThat(favorite.getUser().getId()).isEqualTo(userId);
                assertThat(favorite.getProblem().getId()).isEqualTo(problemId);
            }
        }
    }
}
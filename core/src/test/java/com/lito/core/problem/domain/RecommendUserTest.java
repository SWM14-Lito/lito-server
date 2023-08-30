package com.lito.core.problem.domain;

import com.lito.core.problem.domain.RecommendUser;
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
@DisplayName("RecommendUser")
class RecommendUserTest {

    @Nested
    @DisplayName("createRecommendUser 메서드는")
    class create_recommend_user{

        @Nested
        @DisplayName("userId, problemId를 가지고")
        class with_user_id_problem_id{

            Long userId = 1L;
            Long problemId = 2L;

            @Test
            @DisplayName("RecommendUser를 생성한다.")
            void it_creates_recommend_user() {

                RecommendUser recommendUser = RecommendUser.createRecommendUser(userId, problemId);

                assertThat(recommendUser.getUserId()).isEqualTo(userId);
                assertThat(recommendUser.getProblemId()).isEqualTo(problemId);
            }
        }
    }
}
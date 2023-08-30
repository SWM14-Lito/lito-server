package com.lito.core.user.adapter.out.persistence;

import com.lito.core.user.adapter.out.persistence.UserCommandAdapter;
import com.lito.core.user.adapter.out.persistence.UserRepository;
import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
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
@DisplayName("UserCommandAdapter")
class UserCommandAdapterTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCommandAdapter userCommandAdapter;

    @Nested
    @DisplayName("save 메서드는")
    class save{

        @Nested
        @DisplayName("user entity를 가지고")
        class with_user{
            User user = User.builder()
                    .oauthId("kakaoId")
                    .email("test@test.com")
                    .provider(Provider.KAKAO)
                    .build();
            @Test
            @DisplayName("user를 저장한다.")
            void it_saves_user() {

                assertThatCode(() -> userCommandAdapter.save(user))
                        .doesNotThrowAnyException();
            }

        }
    }
}
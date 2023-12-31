package com.lito.core.user.application.service;

import com.lito.core.common.exception.ApplicationException;
import com.lito.core.user.adapter.out.persistence.UserRepository;
import com.lito.core.user.application.port.in.response.UserResponseDto;
import com.lito.core.user.application.port.out.UserQueryPort;
import com.lito.core.user.application.service.UserQueryService;
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

import static com.lito.core.common.exception.user.UserErrorCode.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("UserQueryService")
class UserQueryServiceTest {

    @Autowired
    UserQueryPort userQueryPort;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserQueryService userQueryService;

    @Nested
    @DisplayName("find 메서드는")
    class find{

        User user = User.builder()
                .oauthId("kakaoId")
                .email("test@test.com")
                .provider(Provider.KAKAO)
                .build();

        @Nested
        @DisplayName("id를 가지고")
        @Transactional
        class with_id{
            Long id = userRepository.save(user).getId();

            @Test
            @DisplayName("UserResponseDto를 리턴한다.")
            void it_returns_user_response_dto() throws Exception {

                UserResponseDto userResponseDto = userQueryService.find(id);

                assertThat(userResponseDto.getUserId()).isEqualTo(id);
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 유저라면")
        class with_user_not_found{
            Long id = 999L;

            @Test
            @DisplayName("USER_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_user_not_found() {

                assertThatThrownBy(() -> userQueryService.find(id))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(USER_NOT_FOUND.getMessage());
            }
        }

    }

    @Nested
    @DisplayName("findAll 메서드는")
    class find_all{

        @BeforeEach
        void setUp(){
            User user = User.builder()
                    .oauthId("kakaoId")
                    .email("test@test.com")
                    .provider(Provider.KAKAO)
                    .build();
            userRepository.save(user);
        }
        @Test
        @DisplayName("모든 유저를 리턴한다.")
        void it_returns_all_user() throws Exception{

            List<User> users = userQueryService.findAll();

            assertThat(users.size()).isEqualTo(1);
        }
    }

}
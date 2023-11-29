package com.lito.core.common.security;

import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.user.UserErrorCode;
import com.lito.core.user.adapter.out.persistence.UserRepository;
import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("CustomUserDetailsService")
class CustomUserDetailsServiceTest {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserRepository userRepository;


    String email = "test@test.com";

    @BeforeEach
    void setUp(){
        userRepository.save(User.builder()
                .oauthId("kakaoId")
                .email(email)
                .provider(Provider.KAKAO)
                .build());
        customUserDetailsService.setProvider(Provider.KAKAO);
    }

    @Nested
    @DisplayName("loadUserByUsername 메서드는")
    class load_user_by_username{

        @Nested
        @DisplayName("email을 가지고")
        class with_email{

            @Test
            @DisplayName("UserDetails를 리턴한다.")
            void it_returns_user_details() throws Exception{

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                assertThat(userDetails.getUsername()).isEqualTo(email);

            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 유저라면")
        class with_user_not_found{

            String notFoundEmail = "email";

            @Test
            @DisplayName("USER_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_user_not_found() throws Exception{

                assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername(notFoundEmail))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(UserErrorCode.USER_NOT_FOUND.getMessage());
            }
        }
    }


    @Nested
    @DisplayName("setProvider 메서드는")
    class set_provider{

        @Nested
        @DisplayName("provider를 가지고")
        class with_provider{

            @Test
            @DisplayName("CustomUserDetailsService에 변수로 등록된다.")
            void it_registers_provider() throws Exception{

                assertThatCode(() -> customUserDetailsService.setProvider(Provider.KAKAO))
                        .doesNotThrowAnyException();
            }
        }
    }

}
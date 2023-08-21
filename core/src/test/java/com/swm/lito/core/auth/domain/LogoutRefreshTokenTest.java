package com.swm.lito.core.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LogoutRefreshToken")
class LogoutRefreshTokenTest {

    @Nested
    @DisplayName("of 메서드는")
    class of{

        @Nested
        @DisplayName("refreshToken과 expiration을 가지고")
        class with_refresh_token_and_expiration{

            String refreshToken = "testRefreshToken";
            long expiration = 10000L;

            @Test
            @DisplayName("logoutRefreshToken entity를 생성한다.")
            void it_makes_logout_refresh_token() {

                LogoutRefreshToken logoutRefreshToken = LogoutRefreshToken.of(refreshToken, expiration);

                assertAll(
                        () -> assertThat(logoutRefreshToken.getId()).isEqualTo(refreshToken),
                        () -> assertThat(logoutRefreshToken.getExpiration()).isEqualTo(expiration)
                );
            }
        }
    }
}
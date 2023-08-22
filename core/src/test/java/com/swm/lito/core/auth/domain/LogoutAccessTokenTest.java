package com.swm.lito.core.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("LogoutAccessToken")
class LogoutAccessTokenTest {

    @Nested
    @DisplayName("of 메서드는")
    class of{

        @Nested
        @DisplayName("accessToken과 expiration을 가지고")
        class with_access_token_and_expiration{

            String accessToken = "testAccessToken";
            long expiration = 1000L;

            @Test
            @DisplayName("logoutAccessToken entity를 생성한다.")
            void it_makes_logout_access_token() {

                LogoutAccessToken logoutAccessToken = LogoutAccessToken.of(accessToken, expiration);

                assertAll(
                        () -> assertThat(logoutAccessToken.getId()).isEqualTo(accessToken),
                        () -> assertThat(logoutAccessToken.getExpiration()).isEqualTo(expiration)
                );
            }
        }
    }
}

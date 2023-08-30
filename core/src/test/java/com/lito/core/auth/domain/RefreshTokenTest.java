package com.lito.core.auth.domain;

import com.lito.core.auth.domain.RefreshToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DisplayName("RefreshToken")
class RefreshTokenTest {

    @Nested
    @DisplayName("of 메서드는")
    class of{

        @Nested
        @DisplayName("username, refreshToken, expiration을 가지고")
        class with_username_and_refresh_token_and_expiration{

            String username = "이름";
            String refreshToken = "testRefreshToken";
            long expiration = 10000L;

            @Test
            @DisplayName("refreshToken entity를 생성한다")
            void it_makes_refresh_token_entity() {

                RefreshToken refreshTokenEntity = RefreshToken.of(username, refreshToken, expiration);

                assertAll(
                        () -> assertThat(refreshTokenEntity.getId()).isEqualTo(username),
                        () -> assertThat(refreshTokenEntity.getRefreshToken()).isEqualTo(refreshToken),
                        () -> assertThat(refreshTokenEntity.getExpiration()).isEqualTo(expiration)
                );
            }
        }
    }
}

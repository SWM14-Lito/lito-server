package com.swm.lito.core.auth.adapter.out.persistence;

import com.swm.lito.core.auth.domain.LogoutAccessToken;
import com.swm.lito.core.auth.domain.LogoutRefreshToken;
import com.swm.lito.core.auth.domain.RefreshToken;
import com.swm.lito.core.common.security.AuthUser;
import com.swm.lito.core.common.security.jwt.JwtProvider;
import com.swm.lito.core.user.domain.User;
import com.swm.lito.core.user.domain.enums.Provider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("TokenAdapter")
class TokenAdapterTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Autowired
    private LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    @Autowired
    private LogoutRefreshTokenRedisRepository logoutRefreshTokenRedisRepository;

    @Autowired
    private TokenAdapter tokenAdapter;

    User user = User.builder()
            .oauthId("kakaoId")
            .email("test@test.com")
            .provider(Provider.KAKAO)
            .build();

    AuthUser authUser = AuthUser.of(user);

    @AfterEach
    void tearDown(){
        refreshTokenRedisRepository.deleteAll();
        logoutAccessTokenRedisRepository.deleteAll();
        logoutRefreshTokenRedisRepository.deleteAll();
    }

    @Nested
    @DisplayName("saveRefreshToken 메서드는")
    class save_refresh_token{

        @Nested
        @DisplayName("refreshToken을 가지고")
        class with_refresh_token{

            String refreshToken = jwtProvider.createRefreshToken(authUser);
            RefreshToken refreshTokenEntity =  RefreshToken.of(authUser.getUsername(),
                    refreshToken,
                    jwtProvider.getRemainingMilliSecondsFromToken(refreshToken));

            @Test
            @DisplayName("redis에 refreshToken을 저장한다.")
            void it_saves_refresh_token() {

                assertThatCode(() -> tokenAdapter.saveRefreshToken(refreshTokenEntity))
                        .doesNotThrowAnyException();
            }
        }
    }

    @Nested
    @DisplayName("saveLogoutAccessToken 메서드는")
    class save_logout_access_token{

        @Nested
        @DisplayName("logoutAccessToken을 가지고")
        class with_logout_access_token{
            String accessToken = jwtProvider.createAccessToken(authUser);
            LogoutAccessToken logoutAccessToken =
                    LogoutAccessToken.of(accessToken, jwtProvider.getRemainingMilliSecondsFromToken(accessToken));

            @Test
            @DisplayName("redis에 logoutAccessToken을 저장한다.")
            void it_saves_logout_access_token() {

                assertThatCode(() -> tokenAdapter.saveLogoutAccessToken(logoutAccessToken))
                        .doesNotThrowAnyException();
            }
        }
    }

    @Nested
    @DisplayName("saveLogoutRefreshToken 메서드는")
    class save_logout_refresh_token{

        @Nested
        @DisplayName("logoutRefreshToken을 가지고")
        class with_logout_refresh_token{

            String refreshToken = jwtProvider.createRefreshToken(authUser);
            LogoutRefreshToken logoutRefreshToken =
                    LogoutRefreshToken.of(refreshToken, jwtProvider.getRemainingMilliSecondsFromToken(refreshToken));

            @Test
            @DisplayName("")
            void it_saves_logout_refresh_token() {

                assertThatCode(() -> tokenAdapter.saveLogoutRefreshToken(logoutRefreshToken))
                        .doesNotThrowAnyException();
            }
        }
    }

    @Nested
    @DisplayName("findRefreshTokenByUsername 메서드는")
    class find_refresh_token_by_username{

        @Nested
        @DisplayName("username을 가지고")
        class with_username{
            String refreshToken = jwtProvider.createRefreshToken(authUser);
            RefreshToken refreshTokenEntity =  RefreshToken.of(authUser.getUsername(),
                    refreshToken,
                    jwtProvider.getRemainingMilliSecondsFromToken(refreshToken));

            @Test
            @DisplayName("refreshToken을 리턴한다.")
            void it_returns_refresh_token() {
                tokenAdapter.saveRefreshToken(refreshTokenEntity);

                Optional<RefreshToken> refreshToken = tokenAdapter.findRefreshTokenByUsername(authUser.getUsername());

                assertThat(refreshToken.get().getId()).isEqualTo(authUser.getUsername());
            }
        }
    }

    @Nested
    @DisplayName("existsLogoutAccessTokenById 메서드는")
    class exists_logout_access_token_by_id{

        @Nested
        @DisplayName("id를 가지고")
        class with_id{
            String accessToken = jwtProvider.createAccessToken(authUser);
            LogoutAccessToken logoutAccessToken =
                    LogoutAccessToken.of(accessToken, jwtProvider.getRemainingMilliSecondsFromToken(accessToken));

            @Test
            @DisplayName("logoutAccessToken이 redis에 존재하는지 여부를 반환한다.")
            void it_returns_whether_logout_access_token_is_in_redis() {
                logoutAccessTokenRedisRepository.save(logoutAccessToken);

                boolean result = tokenAdapter.existsLogoutAccessTokenById(accessToken);

                assertThat(result).isTrue();

            }
        }
    }

    @Nested
    @DisplayName("existsLogoutRefreshTokenById 메서드는")
    class exists_logout_refresh_token_by_id{

        @Nested
        @DisplayName("id를 가지고")
        class with_id{
            String refreshToken = jwtProvider.createRefreshToken(authUser);
            LogoutRefreshToken logoutRefreshToken =
                    LogoutRefreshToken.of(refreshToken, jwtProvider.getRemainingMilliSecondsFromToken(refreshToken));

            @Test
            @DisplayName("logoutRefreshToken이 redis에 존재하는지 여부를 반환한다.")
            void it_returns_whether_logout_refresh_token_is_in_redis() {
                logoutRefreshTokenRedisRepository.save(logoutRefreshToken);

                boolean result = tokenAdapter.existsLogoutRefreshTokenById(refreshToken);

                assertThat(result).isTrue();

            }
        }
    }

}
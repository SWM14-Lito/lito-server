package com.lito.core.auth.application.service;

import com.lito.core.auth.application.port.in.request.LoginRequestDto;
import com.lito.core.auth.application.port.in.response.LoginResponseDto;
import com.lito.core.auth.application.port.in.response.ReissueTokenResponseDto;
import com.lito.core.auth.application.port.out.TokenCommandPort;
import com.lito.core.auth.domain.RefreshToken;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.security.AuthUser;
import com.lito.core.common.security.jwt.JwtProvider;
import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ActiveProfiles("test")
@SpringBootTest
@DisplayName("AuthService")
@Transactional
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    TokenCommandPort tokenCommandPort;

    @Autowired
    JwtProvider jwtProvider;

    @Nested
    @DisplayName("login 메서드는")
    class login {

        @Nested
        @DisplayName("loginRequestDto를 가지고")
        class with_login_request_dto{
            String provider = "kakao";
            LoginRequestDto requestDto = LoginRequestDto
                    .builder()
                    .oauthId("kakaoId")
                    .email("test@test.com")
                    .build();
            @Test
            @DisplayName("LoginResponseDto를 리턴한다.")
            void it_returns_login_response_dto() {

                LoginResponseDto responseDto = authService.login(provider,requestDto);

                assertThat(responseDto.getUserId()).isEqualTo(1L);
            }
        }
    }

    @Nested
    @DisplayName("logout 메서드는")
    class logout {

        @Nested
        @DisplayName("accessToken과 refreshToken을 가지고")
        class with_access_token_refresh_token {
            User user = User.builder()
                    .oauthId("kakaoId")
                    .email("test@test.com")
                    .provider(Provider.KAKAO)
                    .build();
            String accessToken = jwtProvider.createAccessToken(AuthUser.of(user));
            String refreshToken = jwtProvider.createRefreshToken(AuthUser.of(user));

            @Test
            @DisplayName("logout accessToken과 refreshToken으로 저장한다.")
            void it_saves_logout_access_token_and_refresh_token() {

                authService.logout(accessToken, refreshToken);
            }
        }
    }

    @Nested
    @DisplayName("reissue 메서드는")
    class reissue {
        User user = User.builder()
                .oauthId("kakaoId")
                .email("test@test.com")
                .provider(Provider.KAKAO)
                .build();
        AuthUser authUser = AuthUser.of(user);
        @Nested
        @DisplayName("authUser와 refreshToken을 가지고")
        class with_auth_user_refresh_token{

            String refreshToken = jwtProvider.createRefreshToken(authUser);

            @Test
            @DisplayName("ReissueTokenResponseDto를 리턴한다.")
            void it_returns_reissue_token_response_dto() {
                tokenCommandPort.saveRefreshToken(RefreshToken.of
                        (authUser.getUsername(),
                        refreshToken,
                        jwtProvider.getRemainingMilliSecondsFromToken(refreshToken)));

                ReissueTokenResponseDto responseDto = authService.reissue(authUser, refreshToken);

                assertThat(responseDto.getAccessToken()).isNotNull();
                assertThat(responseDto.getRefreshToken()).isNotNull();
            }
        }

        @Nested
        @DisplayName("만약 refreshToken이 존재하지 않는다면")
        class with_not_found_refresh_token{

            User user = User.builder()
                    .oauthId("kakaoId")
                    .email("notFound@test.com")
                    .provider(Provider.KAKAO)
                    .build();

            AuthUser notFoundAuthUser = AuthUser.of(user);
            String refreshToken = "notFoundRefreshToken";

            @Test
            @DisplayName("NOT_FOUND_REFRESH_TOKEN 예외를 발생시킨다.")
            void it_throws_not_found_refresh_token() {

                assertThatThrownBy(() -> authService.reissue(notFoundAuthUser, refreshToken))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage("존재하지 않는 Refresh Token 입니다.");
            }
        }


    }



}
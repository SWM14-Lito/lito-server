package com.swm.lito.auth.application.service;

import com.swm.lito.auth.application.port.in.AuthUseCase;
import com.swm.lito.auth.application.port.in.request.LoginRequestDto;
import com.swm.lito.auth.application.port.in.response.LoginResponseDto;
import com.swm.lito.auth.application.port.out.AuthCommandPort;
import com.swm.lito.auth.application.port.out.AuthQueryPort;
import com.swm.lito.auth.application.port.out.TokenCommandPort;
import com.swm.lito.auth.domain.RefreshToken;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.common.security.jwt.JwtProvider;
import com.swm.lito.user.domain.User;
import com.swm.lito.user.domain.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements AuthUseCase {

    private final AuthQueryPort authQueryPort;
    private final AuthCommandPort authCommandPort;
    private final TokenCommandPort tokenCommandPort;
    private final JwtProvider jwtProvider;

    @Override
    public LoginResponseDto login(Provider provider, LoginRequestDto loginRequestDto) {
        User user = authQueryPort.findByOauthId(loginRequestDto.getOauthId())
                .orElseGet(()->createOauthUser(provider, loginRequestDto));

        AuthUser authUser = AuthUser.of(user);

        String accessToken = jwtProvider.createAccessToken(authUser);
        String refreshToken = createAndSaveRefreshToken(authUser);
        return LoginResponseDto.of(user.getId(), accessToken, refreshToken, user.getNickname()!=null);
    }

    private User createOauthUser(Provider provider, LoginRequestDto loginRequestDto){
        return authCommandPort.save(loginRequestDto.toEntity(provider));
    }

    private String createAndSaveRefreshToken(AuthUser authUser) {
        String refreshToken = jwtProvider.createRefreshToken(authUser);
        tokenCommandPort.saveRefreshToken(
                RefreshToken.of(authUser.getUsername(),
                        refreshToken,
                        jwtProvider.getRemainingMilliSecondsFromToken(refreshToken)));
        return refreshToken;
    }
}

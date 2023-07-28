package com.swm.lito.auth.application.service;

import com.swm.lito.auth.application.port.in.AuthUseCase;
import com.swm.lito.auth.application.port.in.request.LoginRequestDto;
import com.swm.lito.auth.application.port.in.response.LoginResponseDto;
import com.swm.lito.auth.application.port.in.response.ReissueTokenResponseDto;
import com.swm.lito.auth.application.port.out.AuthCommandPort;
import com.swm.lito.auth.application.port.out.AuthQueryPort;
import com.swm.lito.auth.application.port.out.TokenCommandPort;
import com.swm.lito.auth.application.port.out.TokenQueryPort;
import com.swm.lito.auth.domain.LogoutAccessToken;
import com.swm.lito.auth.domain.LogoutRefreshToken;
import com.swm.lito.auth.domain.RefreshToken;
import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.auth.AuthErrorCode;
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
    private final TokenQueryPort tokenQueryPort;
    private final JwtProvider jwtProvider;

    @Override
    public LoginResponseDto login(Provider provider, LoginRequestDto loginRequestDto) {
        User user = authQueryPort.findByOauthIdAndProvider(loginRequestDto.getOauthId(), provider)
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

    @Override
    public void logout(String accessToken, String refreshToken) {
        LogoutAccessToken logoutAccessToken =
                LogoutAccessToken.of(accessToken, jwtProvider.getRemainingMilliSecondsFromToken(accessToken));
        LogoutRefreshToken logoutRefreshToken =
                LogoutRefreshToken.of(refreshToken, jwtProvider.getRemainingMilliSecondsFromToken(refreshToken));

        tokenCommandPort.saveLogoutAccessToken(logoutAccessToken);
        tokenCommandPort.saveLogoutRefreshToken(logoutRefreshToken);
    }

    @Override
    public ReissueTokenResponseDto reissue(AuthUser authUser, String refreshToken) {
        RefreshToken redisRefreshToken = tokenQueryPort.findRefreshTokenByUsername(authUser.getUsername())
                .orElseThrow(() -> new ApplicationException(AuthErrorCode.NOT_FOUND_REFRESH_TOKEN));

        if(jwtProvider.isRefreshTokenValidTime(redisRefreshToken.getExpiration())){
            return ReissueTokenResponseDto.of(jwtProvider.createAccessToken(authUser), refreshToken);
        }
        return ReissueTokenResponseDto.of(jwtProvider.createAccessToken(authUser), createAndSaveRefreshToken(authUser));
    }
}

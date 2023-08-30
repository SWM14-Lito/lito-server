package com.lito.core.auth.application.service;

import com.lito.core.auth.domain.LogoutAccessToken;
import com.lito.core.auth.domain.LogoutRefreshToken;
import com.lito.core.auth.domain.RefreshToken;
import com.lito.core.common.exception.auth.AuthErrorCode;
import com.lito.core.auth.application.port.in.AuthUseCase;
import com.lito.core.auth.application.port.in.request.LoginRequestDto;
import com.lito.core.auth.application.port.in.response.LoginResponseDto;
import com.lito.core.auth.application.port.in.response.ReissueTokenResponseDto;
import com.lito.core.auth.application.port.out.AuthCommandPort;
import com.lito.core.auth.application.port.out.AuthQueryPort;
import com.lito.core.auth.application.port.out.TokenCommandPort;
import com.lito.core.auth.application.port.out.TokenQueryPort;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.security.AuthUser;
import com.lito.core.common.security.jwt.JwtProvider;
import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
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
    public LoginResponseDto login(String provider, LoginRequestDto loginRequestDto) {
        Provider oauthProvider = Provider.toEnum(provider);
        User user = authQueryPort.findByOauthIdAndProvider(loginRequestDto.getOauthId(), oauthProvider)
                .orElseGet(()->createOauthUser(oauthProvider, loginRequestDto));

        AuthUser authUser = AuthUser.of(user);

        String accessToken = jwtProvider.createAccessToken(authUser);
        String refreshToken = createAndSaveRefreshToken(authUser);
        long refreshTokenExpirationTime = jwtProvider.getRemainingMilliSecondsFromToken(refreshToken);
        return LoginResponseDto.of(user.getId(), accessToken, refreshToken,
                user.getNickname()!=null, refreshTokenExpirationTime);
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
            return ReissueTokenResponseDto.of(jwtProvider.createAccessToken(authUser), refreshToken.substring(7),
                    redisRefreshToken.getExpiration());
        }
        String reissuedAccessToken = jwtProvider.createAccessToken(authUser);
        String reissuedRefreshToken = createAndSaveRefreshToken(authUser);
        long refreshTokenExpirationTime = jwtProvider.getRemainingMilliSecondsFromToken(reissuedRefreshToken);
        return ReissueTokenResponseDto.of(reissuedAccessToken, reissuedRefreshToken, refreshTokenExpirationTime);
    }
}

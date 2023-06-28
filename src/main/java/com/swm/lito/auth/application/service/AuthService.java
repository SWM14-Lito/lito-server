package com.swm.lito.auth.application.service;

import com.swm.lito.auth.adapter.out.oauth.OauthUserInfo;
import com.swm.lito.auth.application.port.in.AuthUseCase;
import com.swm.lito.auth.application.port.in.response.LoginResponseDto;
import com.swm.lito.auth.application.port.out.AuthCommandPort;
import com.swm.lito.auth.application.port.out.AuthQueryPort;
import com.swm.lito.auth.application.port.out.OauthPort;
import com.swm.lito.auth.application.port.out.TokenCommandPort;
import com.swm.lito.auth.domain.RefreshToken;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.common.security.jwt.JwtProvider;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.swm.lito.user.domain.enums.Provider.toEnum;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final AuthQueryPort authQueryPort;
    private final AuthCommandPort authCommandPort;
    private final OauthPort oauthPort;
    private final TokenCommandPort tokenCommandPort;
    private final JwtProvider jwtProvider;

    @Override
    public LoginResponseDto login(String provider, String OauthAccessToken) {
        OauthUserInfo oauthUserInfo = oauthPort.getUserInfo(toEnum(provider),OauthAccessToken);
        User user = authQueryPort.findByEmail(oauthUserInfo.getEmail())
                .orElseGet(()->createOauthUser(oauthUserInfo));

        AuthUser authUser = AuthUser.of(user);

        String accessToken = jwtProvider.createAccessToken(authUser);
        String refreshToken = createAndSaveRefreshToken(authUser);
        return LoginResponseDto.of(user.getId(), accessToken, refreshToken, isRegistered(user.getNickname()));
    }

    private boolean isRegistered(String nickname){
        return nickname!=null;
    }

    private User createOauthUser(OauthUserInfo client){
        return authCommandPort.save(User.builder()
                .email(client.getEmail())
                .name(client.getName())
                .provider(client.getProvider())
                .build());
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

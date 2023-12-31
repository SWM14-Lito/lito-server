package com.lito.core.auth.application.port.in.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    private Long userId;

    private String accessToken;

    private String refreshToken;

    private boolean registered;

    private long refreshTokenExpirationTime;

    public static LoginResponseDto of(Long userId, String accessToken, String refreshToken,
                                      boolean registered, long refreshTokenExpirationTime){
        return LoginResponseDto.builder()
                .userId(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .registered(registered)
                .refreshTokenExpirationTime(refreshTokenExpirationTime)
                .build();
    }
}

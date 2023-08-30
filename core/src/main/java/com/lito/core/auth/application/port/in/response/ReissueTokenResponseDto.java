package com.lito.core.auth.application.port.in.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReissueTokenResponseDto {

    private String accessToken;

    private String refreshToken;

    private long refreshTokenExpirationTime;

    public static ReissueTokenResponseDto of(String accessToken, String refreshToken, long refreshTokenExpirationTime){
        return ReissueTokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(refreshTokenExpirationTime)
                .build();
    }
}

package com.lito.api.auth.adapter.in.response;

import com.lito.core.auth.application.port.in.response.ReissueTokenResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReissueTokenResponse {

    private String accessToken;

    private String refreshToken;

    private long refreshTokenExpirationTime;

    public static ReissueTokenResponse from(ReissueTokenResponseDto dto){
        return ReissueTokenResponse.builder()
                .accessToken(dto.getAccessToken())
                .refreshToken(dto.getRefreshToken())
                .refreshTokenExpirationTime(dto.getRefreshTokenExpirationTime())
                .build();
    }
}

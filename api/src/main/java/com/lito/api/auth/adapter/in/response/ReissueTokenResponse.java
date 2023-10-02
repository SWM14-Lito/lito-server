package com.lito.api.auth.adapter.in.response;

import com.lito.core.auth.application.port.in.response.ReissueTokenResponseDto;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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

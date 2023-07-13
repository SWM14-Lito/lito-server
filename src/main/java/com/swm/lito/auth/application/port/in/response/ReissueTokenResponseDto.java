package com.swm.lito.auth.application.port.in.response;

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

    public static ReissueTokenResponseDto of(String accessToken, String refreshToken){
        return ReissueTokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

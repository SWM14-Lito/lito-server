package com.swm.lito.auth.adapter.in.response;

import com.swm.lito.auth.application.port.in.response.ReissueTokenResponseDto;
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

    public static ReissueTokenResponse from(ReissueTokenResponseDto dto){
        return ReissueTokenResponse.builder()
                .accessToken(dto.getAccessToken())
                .refreshToken(dto.getRefreshToken())
                .build();
    }
}

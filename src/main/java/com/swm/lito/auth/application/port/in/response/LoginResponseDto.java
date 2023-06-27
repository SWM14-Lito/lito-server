package com.swm.lito.auth.application.port.in.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    private String accessToken;

    private String refreshToken;

    private boolean registered;

    public static LoginResponseDto of(String accessToken, String refreshToken, boolean registered){
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .registered(registered)
                .build();
    }
}

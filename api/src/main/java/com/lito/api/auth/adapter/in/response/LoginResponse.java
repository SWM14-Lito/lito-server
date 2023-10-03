package com.lito.api.auth.adapter.in.response;

import com.lito.core.auth.application.port.in.response.LoginResponseDto;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginResponse {

    private Long userId;

    private String accessToken;

    private String refreshToken;

    private boolean registered;

    private long refreshTokenExpirationTime;

    public static LoginResponse from(LoginResponseDto dto){
        return LoginResponse.builder()
                .userId(dto.getUserId())
                .accessToken(dto.getAccessToken())
                .refreshToken(dto.getRefreshToken())
                .registered(dto.isRegistered())
                .refreshTokenExpirationTime(dto.getRefreshTokenExpirationTime())
                .build();

    }
}

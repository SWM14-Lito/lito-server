package com.swm.lito.auth.adapter.in.response;

import com.swm.lito.auth.application.port.in.response.LoginResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private Long userId;

    private String accessToken;

    private String refreshToken;

    private boolean registered;

    public static LoginResponse from(LoginResponseDto dto){
        return LoginResponse.builder()
                .userId(dto.getUserId())
                .accessToken(dto.getAccessToken())
                .refreshToken(dto.getRefreshToken())
                .registered(dto.isRegistered())
                .build();

    }
}

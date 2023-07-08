package com.swm.lito.auth.adapter.in.request;

import com.swm.lito.auth.application.port.in.request.LoginRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    private String oauthId;

    private String email;


    public LoginRequestDto toRequestDto(){
        return LoginRequestDto.builder()
                .oauthId(oauthId)
                .email(email)
                .build();
    }
}

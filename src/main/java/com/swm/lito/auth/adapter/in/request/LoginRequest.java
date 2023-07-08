package com.swm.lito.auth.adapter.in.request;

import com.swm.lito.auth.application.port.in.request.LoginRequestDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "oauthId를 입력해주세요.")
    private String oauthId;
    @Email
    private String email;


    public LoginRequestDto toRequestDto(){
        return LoginRequestDto.builder()
                .oauthId(oauthId)
                .email(email)
                .build();
    }
}

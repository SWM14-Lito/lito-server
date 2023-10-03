package com.lito.api.auth.adapter.in.request;

import com.lito.core.auth.application.port.in.request.LoginRequestDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "oauthId를 입력해주세요.")
    private String oauthId;
    @Email(message = "이메일 형식을 확인해주세요.")
    private String email;


    public LoginRequestDto toRequestDto(){
        return LoginRequestDto.builder()
                .oauthId(oauthId)
                .email(email)
                .build();
    }
}

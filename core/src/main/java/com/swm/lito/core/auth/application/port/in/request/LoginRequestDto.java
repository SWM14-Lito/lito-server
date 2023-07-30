package com.swm.lito.core.auth.application.port.in.request;

import com.swm.lito.core.user.domain.User;
import com.swm.lito.core.user.domain.enums.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto {

    private String oauthId;

    private String email;

    public User toEntity(Provider provider){
        return User.builder()
                .oauthId(oauthId)
                .email(email)
                .provider(provider)
                .build();
    }
}

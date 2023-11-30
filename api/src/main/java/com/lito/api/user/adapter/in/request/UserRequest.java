package com.lito.api.user.adapter.in.request;

import com.lito.core.user.application.port.in.request.UserRequestDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Length(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 작성해주세요.")
    private String nickname;

    private String introduce;

    public UserRequestDto toRequestDto(){
        return UserRequestDto.builder()
                .nickname(nickname)
                .introduce(introduce)
                .build();
    }
}

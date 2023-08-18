package com.swm.lito.api.user.adapter.in.request;

import com.swm.lito.core.user.application.port.in.request.ProfileRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequest {

    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 작성해주세요.")
    private String nickname;

    private String introduce;

    public ProfileRequestDto toRequestDto(){
        return ProfileRequestDto.builder()
                .nickname(nickname)
                .introduce(introduce)
                .build();
    }
}

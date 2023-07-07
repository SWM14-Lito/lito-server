package com.swm.lito.user.adapter.in.request;

import com.swm.lito.user.application.port.in.request.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    private String nickname;

    private String introduce;

    private String name;

    public UserRequestDto toRequestDto(){
        return UserRequestDto.builder()
                .nickname(nickname)
                .introduce(introduce)
                .name(name)
                .build();
    }
}

package com.swm.lito.user.adapter.in.response;

import com.swm.lito.user.application.port.in.response.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long userId;

    private String profileImgUrl;

    private int point;

    private String nickname;

    private String introduce;

    public static UserResponse from (UserResponseDto dto){
        return UserResponse.builder()
                .userId(dto.getUserId())
                .profileImgUrl(dto.getProfileImgUrl())
                .point(dto.getPoint())
                .nickname(dto.getNickname())
                .introduce(dto.getIntroduce())
                .build();
    }
}

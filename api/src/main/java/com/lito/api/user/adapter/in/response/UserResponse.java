package com.lito.api.user.adapter.in.response;

import com.lito.core.user.application.port.in.response.UserResponseDto;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserResponse {

    private Long userId;

    private String profileImgUrl;

    private int point;

    private String nickname;

    private String introduce;

    private String alarmStatus;

    public static UserResponse from (UserResponseDto dto){
        return UserResponse.builder()
                .userId(dto.getUserId())
                .profileImgUrl(dto.getProfileImgUrl())
                .point(dto.getPoint())
                .nickname(dto.getNickname())
                .introduce(dto.getIntroduce())
                .alarmStatus(dto.getAlarmStatus())
                .build();
    }
}

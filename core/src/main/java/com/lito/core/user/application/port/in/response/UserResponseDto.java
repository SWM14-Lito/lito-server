package com.lito.core.user.application.port.in.response;

import com.lito.core.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Long userId;

    private String profileImgUrl;

    private int point;

    private String nickname;

    private String introduce;

    private String alarmStatus;

    public static UserResponseDto from(User user){
        return UserResponseDto.builder()
                .userId(user.getId())
                .profileImgUrl(user.getProfileImgUrl())
                .point(user.getPoint())
                .nickname(user.getNickname())
                .introduce(user.getIntroduce())
                .alarmStatus(user.getAlarmStatus())
                .build();
    }
}

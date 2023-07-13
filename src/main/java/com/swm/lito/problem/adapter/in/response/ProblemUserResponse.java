package com.swm.lito.problem.adapter.in.response;

import com.swm.lito.problem.application.port.in.response.ProblemUserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemUserResponse {

    private Long userId;

    private String profileImgUrl;

    private String nickname;

    private Long problemId;

    private String subject;

    private String question;
    //찜한 문제 여부
    private boolean favorite;

    public static ProblemUserResponse from(ProblemUserResponseDto dto){
        return ProblemUserResponse.builder()
                .userId(dto.getUserId())
                .profileImgUrl(dto.getProfileImgUrl())
                .nickname(dto.getNickname())
                .problemId(dto.getProblemId())
                .subject(dto.getSubject())
                .question(dto.getQuestion())
                .favorite(dto.isFavorite())
                .build();
    }
}

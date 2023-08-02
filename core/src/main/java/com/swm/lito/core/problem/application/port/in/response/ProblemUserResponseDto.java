package com.swm.lito.core.problem.application.port.in.response;

import com.swm.lito.core.problem.domain.Problem;
import com.swm.lito.core.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemUserResponseDto {

    private Long userId;

    private String profileImgUrl;

    private String nickname;

    private Long problemId;

    private String subject;

    private String question;
    //찜한 문제 여부
    private boolean favorite;

    public static ProblemUserResponseDto of(User user, Problem problem, boolean favorite){
        return ProblemUserResponseDto.builder()
                .userId(user.getId())
                .profileImgUrl(user.getProfileImgUrl())
                .nickname(user.getNickname())
                .problemId(problem.getId())
                .subject(problem.getSubject().getSubjectName())
                .question(problem.getQuestion())
                .favorite(favorite)
                .build();
    }

    public static ProblemUserResponseDto of(User user){
        return ProblemUserResponseDto.builder()
                .userId(user.getId())
                .profileImgUrl(user.getProfileImgUrl())
                .nickname(user.getNickname())
                .favorite(false)
                .build();
    }
}
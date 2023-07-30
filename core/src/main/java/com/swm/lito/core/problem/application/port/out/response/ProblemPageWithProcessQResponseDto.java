package com.swm.lito.core.problem.application.port.out.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemPageWithProcessQResponseDto {

    private Long problemUserId;

    private Long problemId;

    private String subjectName;

    private String question;

    private boolean favorite;

    @QueryProjection
    public ProblemPageWithProcessQResponseDto(Long problemUserId, Long problemId, String subjectName, String question) {
        this.problemUserId = problemUserId;
        this.problemId = problemId;
        this.subjectName = subjectName;
        this.question = question;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}

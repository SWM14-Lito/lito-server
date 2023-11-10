package com.lito.core.problem.application.port.out.response;

import com.lito.core.problem.domain.enums.ProblemStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemReviewPageQResponseDto {

    private Long problemId;

    private String subjectName;

    private String question;

    private ProblemStatus problemStatus;

    private boolean favorite;

    private int unsolvedCnt;

    @QueryProjection
    public ProblemReviewPageQResponseDto(Long problemId, String subjectName, String question, ProblemStatus problemStatus, int unsolvedCnt) {
        this.problemId = problemId;
        this.subjectName = subjectName;
        this.question = question;
        this.problemStatus = problemStatus;
        this.unsolvedCnt = unsolvedCnt;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

}

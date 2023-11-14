package com.lito.api.problem.adapter.in.response;

import com.lito.core.problem.application.port.out.response.ProblemReviewPageQResponseDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProblemReviewPage {

    private Long problemId;

    private String subjectName;

    private String question;

    private String problemStatus;

    private boolean favorite;

    private int unsolvedCnt;

    public static List<ProblemReviewPage> from(List<ProblemReviewPageQResponseDto> dtos){
        return dtos.stream()
                .map(ProblemReviewPage::from)
                .collect(Collectors.toList());
    }

    private static ProblemReviewPage from(ProblemReviewPageQResponseDto dto){
        return ProblemReviewPage.builder()
                .problemId(dto.getProblemId())
                .subjectName(dto.getSubjectName())
                .question(dto.getQuestion())
                .problemStatus(dto.getProblemStatus().getName())
                .favorite(dto.isFavorite())
                .unsolvedCnt(dto.getUnsolvedCnt())
                .build();
    }
}

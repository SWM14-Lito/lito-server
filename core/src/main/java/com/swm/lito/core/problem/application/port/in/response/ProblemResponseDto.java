package com.swm.lito.core.problem.application.port.in.response;

import com.swm.lito.core.problem.domain.Problem;
import com.swm.lito.core.problem.domain.ProblemUser;
import com.swm.lito.core.problem.domain.enums.ProblemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemResponseDto {

    private Long problemId;

    private String problemQuestion;

    private String problemAnswer;

    private String problemKeyword;

    private String problemStatus;

    private boolean favorite;

    @Builder.Default
    private List<FaqResponseDto> faqResponseDtos = new ArrayList<>();

    public static ProblemResponseDto of(Problem problem, ProblemUser problemUser, boolean favorite){
        return ProblemResponseDto.builder()
                .problemId(problem.getId())
                .problemQuestion(problem.getQuestion())
                .problemAnswer(problem.getAnswer())
                .problemKeyword(problem.getKeyword())
                .problemStatus(problemUser == null ? ProblemStatus.NOT_SEEN.getName()
                        : problemUser.getProblemStatus().getName())
                .favorite(favorite)
                .faqResponseDtos(FaqResponseDto.from(problem.getFaqs()))
                .build();
    }
}

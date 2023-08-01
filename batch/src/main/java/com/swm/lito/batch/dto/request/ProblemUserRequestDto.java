package com.swm.lito.batch.dto.request;

import com.swm.lito.core.problem.domain.ProblemUser;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProblemUserRequestDto {

    private Long userId;

    private Long problemId;

    private String problemStatus;

    private int unsolvedCnt;

    public static ProblemUserRequestDto from(ProblemUser problemUser){
        return ProblemUserRequestDto.builder()
                .userId(problemUser.getUser().getId())
                .problemId(problemUser.getProblem().getId())
                .problemStatus(problemUser.getProblemStatus().getName())
                .unsolvedCnt(problemUser.getUnsolvedCnt())
                .build();
    }
}

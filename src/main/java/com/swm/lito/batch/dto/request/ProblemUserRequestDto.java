package com.swm.lito.batch.dto.request;

import com.swm.lito.problem.domain.ProblemUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemUserRequestDto {

    private Long userId;

    private Long problemId;

    private String problemStatus;

    private int unsolvedCnt;

    public static ProblemUserRequestDto from(ProblemUser problemUser){
        return ProblemUserRequestDto.builder()
                .userId(problemUser.getId())
                .problemId(problemUser.getProblem().getId())
                .problemStatus(problemUser.getProblemStatus().getName())
                .unsolvedCnt(problemUser.getUnsolvedCnt())
                .build();
    }
}

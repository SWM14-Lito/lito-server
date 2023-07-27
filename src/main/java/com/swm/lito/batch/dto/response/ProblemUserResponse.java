package com.swm.lito.batch.dto.response;

import com.swm.lito.problem.domain.ProblemUser;
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

    private Long problemId;

    private String problemStatus;

    public static ProblemUserResponse from(ProblemUser problemUser){
        return ProblemUserResponse.builder()
                .userId(problemUser.getId())
                .problemId(problemUser.getProblem().getId())
                .problemStatus(problemUser.getProblemStatus().getName())
                .build();
    }
}

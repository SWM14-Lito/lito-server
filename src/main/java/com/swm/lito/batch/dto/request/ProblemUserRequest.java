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
public class ProblemUserRequest {

    private Long userId;

    private Long problemId;

    private String problemStatus;

    public static ProblemUserRequest from(ProblemUser problemUser){
        return ProblemUserRequest.builder()
                .userId(problemUser.getId())
                .problemId(problemUser.getProblem().getId())
                .problemStatus(problemUser.getProblemStatus().getName())
                .build();
    }
}

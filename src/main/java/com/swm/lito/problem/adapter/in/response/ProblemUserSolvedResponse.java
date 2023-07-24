package com.swm.lito.problem.adapter.in.response;

import com.swm.lito.problem.application.port.in.response.ProblemUserSolvedResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemUserSolvedResponse {

    private boolean solved;

    public static ProblemUserSolvedResponse from(ProblemUserSolvedResponseDto dto){
        return ProblemUserSolvedResponse.builder()
                .solved(dto.isSolved())
                .build();
    }
}

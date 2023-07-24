package com.swm.lito.problem.application.port.in.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemUserSolvedResponseDto {

    private boolean solved;

    public static ProblemUserSolvedResponseDto from(boolean solved){
        return ProblemUserSolvedResponseDto.builder()
                .solved(solved)
                .build();
    }
}

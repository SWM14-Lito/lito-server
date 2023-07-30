package com.swm.lito.core.problem.application.port.in.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemSubmitResponseDto {

    private boolean solved;

    public static ProblemSubmitResponseDto from(boolean solved){
        return ProblemSubmitResponseDto.builder()
                .solved(solved)
                .build();
    }
}

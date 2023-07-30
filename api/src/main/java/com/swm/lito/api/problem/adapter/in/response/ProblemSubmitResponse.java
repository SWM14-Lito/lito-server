package com.swm.lito.api.problem.adapter.in.response;

import com.swm.lito.core.problem.application.port.in.response.ProblemSubmitResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemSubmitResponse {

    private boolean solved;

    public static ProblemSubmitResponse from(ProblemSubmitResponseDto dto){
        return ProblemSubmitResponse.builder()
                .solved(dto.isSolved())
                .build();
    }
}

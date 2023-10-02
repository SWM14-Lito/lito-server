package com.lito.api.problem.adapter.in.response;

import com.lito.core.problem.application.port.in.response.ProblemSubmitResponseDto;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProblemSubmitResponse {

    private boolean solved;

    public static ProblemSubmitResponse from(ProblemSubmitResponseDto dto){
        return ProblemSubmitResponse.builder()
                .solved(dto.isSolved())
                .build();
    }
}

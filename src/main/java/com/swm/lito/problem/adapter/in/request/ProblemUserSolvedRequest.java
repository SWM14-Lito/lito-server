package com.swm.lito.problem.adapter.in.request;

import com.swm.lito.problem.application.port.in.request.ProblemUserSolvedRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemUserSolvedRequest {

    private String keyword;

    public ProblemUserSolvedRequestDto toRequestDto(){
        return ProblemUserSolvedRequestDto.builder()
                .keyword(keyword)
                .build();
    }
}

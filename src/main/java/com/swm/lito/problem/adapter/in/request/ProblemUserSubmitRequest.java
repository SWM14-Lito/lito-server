package com.swm.lito.problem.adapter.in.request;

import com.swm.lito.problem.application.port.in.request.ProblemUserSubmitRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemUserSubmitRequest {

    private String keyword;

    public ProblemUserSubmitRequestDto toRequestDto(){
        return ProblemUserSubmitRequestDto.builder()
                .keyword(keyword)
                .build();
    }
}

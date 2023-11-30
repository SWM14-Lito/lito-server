package com.lito.api.problem.adapter.in.request;

import com.lito.core.problem.application.port.in.request.ProblemSubmitRequestDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemSubmitRequest {

    @NotBlank(message = "키워드를 입력해주세요.")
    private String keyword;

    public ProblemSubmitRequestDto toRequestDto(){
        return ProblemSubmitRequestDto.builder()
                .keyword(keyword)
                .build();
    }
}

package com.swm.lito.admin.problem.adapter.in.request;

import com.swm.lito.core.admin.application.port.in.request.FaqRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaqRequest {

    private String question;

    private String answer;

    public FaqRequestDto toRequestDto(){
        return FaqRequestDto.builder()
                .question(question)
                .answer(answer)
                .build();
    }
}

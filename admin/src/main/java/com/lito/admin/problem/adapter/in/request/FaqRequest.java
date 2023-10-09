package com.lito.admin.problem.adapter.in.request;

import com.lito.core.admin.application.port.in.request.FaqRequestDto;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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

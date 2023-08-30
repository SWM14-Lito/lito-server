package com.swm.lito.core.admin.application.port.in.request;

import com.swm.lito.core.problem.domain.Faq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaqRequestDto {

    private String question;

    private String answer;

    public Faq toEntity(){
        return Faq.builder()
                .question(question)
                .answer(answer)
                .build();
    }
}

package com.lito.core.problem.application.port.in.response;

import com.lito.core.problem.domain.Faq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaqResponseDto {

    private String faqQuestion;

    private String faqAnswer;

    public static FaqResponseDto from(Faq faq){
        return FaqResponseDto.builder()
                .faqQuestion(faq.getQuestion())
                .faqAnswer(faq.getAnswer())
                .build();
    }

    public static List<FaqResponseDto> from(List<Faq> faqs){
        return faqs.stream()
                .map(FaqResponseDto::from)
                .collect(Collectors.toList());
    }
}

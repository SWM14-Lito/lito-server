package com.lito.admin.problem.adapter.in.request;

import com.lito.core.admin.application.port.in.request.ProblemRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProblemRequest {

    @NotNull(message = "subjectId를 입력해주세요.")
    @Positive(message = "subjectId는 양수만 가능합니다.")
    private Long subjectId;

    @NotNull(message = "subjectCategoryId를 입력해주세요.")
    @Positive(message = "subjectCategoryId는 양수만 가능합니다.")
    private Long subjectCategoryId;

    @NotBlank(message = "질문을 입력해주세요.")
    private String question;

    @NotBlank(message = "답변을 입력해주세요.")
    private String answer;

    @NotBlank(message = "키워드를 입력해주세요.")
    private String keyword;

    @Builder.Default
    private List<FaqRequest> faqs = new ArrayList<>();

    public ProblemRequestDto toRequestDto(){
        return ProblemRequestDto.builder()
                .subjectId(subjectId)
                .subjectCategoryId(subjectCategoryId)
                .question(question)
                .answer(answer)
                .keyword(keyword)
                .faqRequestDtos(faqs.stream()
                        .map(FaqRequest::toRequestDto)
                        .collect(Collectors.toList()))
                .build();
    }
}

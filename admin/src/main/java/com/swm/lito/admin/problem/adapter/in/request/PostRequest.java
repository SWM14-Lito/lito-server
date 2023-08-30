package com.swm.lito.admin.problem.adapter.in.request;

import com.swm.lito.core.admin.application.port.in.request.PostRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {

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

    public PostRequestDto toRequestDto(){
        return PostRequestDto.builder()
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

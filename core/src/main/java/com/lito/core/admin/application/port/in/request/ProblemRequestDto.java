package com.lito.core.admin.application.port.in.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemRequestDto {

    private Long subjectId;

    private Long subjectCategoryId;

    private String question;

    private String answer;

    private String keyword;

    @Builder.Default
    private List<FaqRequestDto> faqRequestDtos = new ArrayList<>();
}

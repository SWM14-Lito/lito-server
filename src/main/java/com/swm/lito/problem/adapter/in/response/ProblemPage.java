package com.swm.lito.problem.adapter.in.response;

import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
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
public class ProblemPage {

    private Long problemId;

    private String subjectName;

    private String question;

    private String problemStatus;

    private boolean favorite;

    public static List<ProblemPage> from(List<ProblemPageQueryDslResponseDto> dtos){
        return dtos.stream()
                .map(ProblemPage::from)
                .collect(Collectors.toList());
    }
    private static ProblemPage from(ProblemPageQueryDslResponseDto dto){
        return ProblemPage.builder()
                .problemId(dto.getProblemId())
                .subjectName(dto.getSubjectName())
                .question(dto.getQuestion())
                .problemStatus(dto.getProblemStatus().getName())
                .favorite(dto.isFavorite())
                .build();
    }
}

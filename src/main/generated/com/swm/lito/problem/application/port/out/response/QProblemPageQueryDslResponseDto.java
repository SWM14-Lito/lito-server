package com.swm.lito.problem.application.port.out.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.swm.lito.problem.application.port.out.response.QProblemPageQueryDslResponseDto is a Querydsl Projection type for ProblemPageQueryDslResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProblemPageQueryDslResponseDto extends ConstructorExpression<ProblemPageQueryDslResponseDto> {

    private static final long serialVersionUID = 1924791148L;

    public QProblemPageQueryDslResponseDto(com.querydsl.core.types.Expression<Long> problemId, com.querydsl.core.types.Expression<String> subjectName, com.querydsl.core.types.Expression<String> question, com.querydsl.core.types.Expression<com.swm.lito.problem.domain.enums.ProblemStatus> problemStatus) {
        super(ProblemPageQueryDslResponseDto.class, new Class<?>[]{long.class, String.class, String.class, com.swm.lito.problem.domain.enums.ProblemStatus.class}, problemId, subjectName, question, problemStatus);
    }

}


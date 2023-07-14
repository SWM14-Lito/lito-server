package com.swm.lito.common.exception.problem;

import com.swm.lito.common.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemErrorCode implements ErrorEnumCode {
    PROBLEM_NOT_FOUND("P001","존재하지 않는 문제입니다."),
    ;

    private final String code;
    private final String message;
}

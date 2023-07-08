package com.swm.lito.common.exception.problem;

import com.swm.lito.common.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemErrorCode implements ErrorEnumCode {
    INVALID_PROBLEM("P001","지원하지 않는 Problem 상태값 입니다.")
    ;

    private final String code;
    private final String message;
}

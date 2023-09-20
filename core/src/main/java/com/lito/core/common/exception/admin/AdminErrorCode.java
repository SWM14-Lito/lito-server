package com.lito.core.common.exception.admin;

import com.lito.core.common.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminErrorCode implements ErrorEnumCode {

    SUBJECT_NOT_FOUND("ADMIN001", "존재하지 않는 과목입니다."),
    SUBJECT_CATEGORY_NOT_FOUND("ADMIN002", "존재하지 않는 목차입니다."),
    PROBLEM_NOT_FOUND("ADMIN003","존재하지 않는 문제입니다.")
    ;

    private final String code;
    private final String message;
}

package com.swm.lito.common.exception.company;

import com.swm.lito.common.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompanyErrorCode implements ErrorEnumCode {
    INVALID_CAREER("C001","지원하지 않는 Career 상태값 입니다."),
    INVALID_INTERVIEW("C002","지원하지 않는 Interview 상태값 입니다."),
    INVALID_RESULT("C003","지원하지 않는 Result 상태값 입니다."),
    INVALID_UPLOAD("C004", "지원하지 않는 Upload 상태값 입니다.")
    ;
    private final String code;
    private final String message;
}

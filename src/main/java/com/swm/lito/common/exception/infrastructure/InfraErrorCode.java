package com.swm.lito.common.exception.infrastructure;

import com.swm.lito.common.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InfraErrorCode implements ErrorEnumCode {
    INVALID_OAUTH("I001","지원되지 않는 Oauth 제공자입니다."),
    ;
    private final String code;
    private final String message;
}

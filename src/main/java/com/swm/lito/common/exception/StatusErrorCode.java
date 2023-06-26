package com.swm.lito.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusErrorCode implements ErrorEnumCode{

    EMPTY_STATUS("S001","상태 코드를 입력해주세요."),
    INVALID_STATUS("S002","상태 코드 형식을 확인해주세요.");


    private final String code;
    private String message;
}

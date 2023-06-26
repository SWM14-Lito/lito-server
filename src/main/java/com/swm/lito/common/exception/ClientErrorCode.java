package com.swm.lito.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientErrorCode implements ErrorEnumCode{

    BIND_EXCEPTION("V001", "입력 값에 대한 예외입니다."),
    TYPE_MISMATCH_EXCEPTION("V002", "형변환 중 예외가 발생했습니다. 값을 입력형식에 맞게 입력해주세요."),
    ;

    private final String code;
    private String message;
}

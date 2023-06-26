package com.swm.lito.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServerErrorCode implements ErrorEnumCode{
    SERVER_ERROR("SERVER","서버와의 연결에 실패했습니다."),
    ;

    private final String code;
    private String message;
}

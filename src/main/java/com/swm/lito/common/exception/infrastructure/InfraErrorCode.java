package com.swm.lito.common.exception.infrastructure;

import com.swm.lito.common.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InfraErrorCode implements ErrorEnumCode {
    INVALID_OAUTH("I001","지원되지 않는 Oauth 제공자입니다."),
    FILE_UPLOAD_FAIL("I002", "파일 업로드에 실패했습니다."),
    URL_DECODE_ERROR("I003", "URL을 복호화 할 수 없습니다.")
    ;
    private final String code;
    private final String message;
}

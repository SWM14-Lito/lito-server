package com.swm.lito.core.common.exception.batch;

import com.swm.lito.core.common.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BatchErrorCode implements ErrorEnumCode {

    BATCH_NOT_FOUND("B001","존재하지 않는 배치 작업 입니다.")
    ;

    private final String code;
    private String message;
}

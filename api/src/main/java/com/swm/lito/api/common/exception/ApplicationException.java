package com.swm.lito.api.common.exception;

import com.swm.lito.core.common.exception.ErrorEnumCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private final ErrorEnumCode errorEnumCode;

    public ApplicationException(ErrorEnumCode errorEnumCode) {
        super(errorEnumCode.getMessage());
        this.errorEnumCode = errorEnumCode;
    }

    public ApplicationException(ErrorEnumCode errorEnumCode, String message) {
        super(message);
        this.errorEnumCode = errorEnumCode;
    }
}

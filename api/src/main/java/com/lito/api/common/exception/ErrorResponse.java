package com.lito.api.common.exception;

import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.ClientErrorCode;
import com.lito.core.common.exception.ErrorEnumCode;
import com.lito.core.common.exception.ServerErrorCode;
import com.lito.core.common.exception.auth.AuthErrorCode;
import com.lito.core.common.exception.file.FileErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private LocalDateTime time;
    private int status;
    private String message;
    private String code;
    private List<FieldError> errors;

    private ErrorResponse(BindingResult bindingResult) {
        this.time = LocalDateTime.now();
        this.status = HttpStatus.BAD_REQUEST.value();
        this.message = "입력 조건에 대한 예외입니다.";
        this.code = ClientErrorCode.INVALID_VALUE.getCode();
        this.errors = FieldError.of(bindingResult);
    }

    private ErrorResponse(ApplicationException e) {
        this.time = LocalDateTime.now();
        this.status = HttpStatus.BAD_REQUEST.value();
        this.message = e.getMessage();
        this.code = e.getErrorEnumCode().getCode();
        this.errors = Collections.emptyList();
    }

    private ErrorResponse(int status, ErrorEnumCode errorEnumCode) {
        this.time = LocalDateTime.now();
        this.status = status;
        this.message = errorEnumCode.getMessage();
        this.code = errorEnumCode.getCode();
        this.errors = Collections.emptyList();
    }

    private ErrorResponse(int status, String message, ErrorEnumCode errorEnumCode) {
        this.time = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.code = errorEnumCode.getCode();
        this.errors = Collections.emptyList();
    }

    public static ErrorResponse fromBindException(BindingResult bindingResult) {
        return new ErrorResponse(bindingResult);
    }

    public static ErrorResponse from(ApplicationException e) {
        return new ErrorResponse(e);
    }

    public static ErrorResponse fromEmptyFileException(){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), FileErrorCode.FILE_EMPTY);
    }

    public static ErrorResponse fromExceededSizeException(){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), FileErrorCode.FILE_EXCEEDED_SIZE);
    }

    public static ErrorResponse fromEmptyQueryStringException(){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ClientErrorCode.EMPTY_QUERY_STRING);
    }

    public static ErrorResponse fromEmptyPathVariableException(){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ClientErrorCode.EMPTY_PATH_VARIABLE);
    }

    public static ErrorResponse fromServerException(Exception e){
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), ServerErrorCode.SERVER_ERROR);
    }

    public static ErrorResponse fromUnauthorizedAtFilter() {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), AuthErrorCode.UNAUTHORIZED);
    }

    public static ErrorResponse fromForbiddenAtFilter() {
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), AuthErrorCode.FORBIDDEN);
    }

    public static ErrorResponse fromJwtExceptionFilter(ErrorEnumCode errorEnumCode){
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), errorEnumCode);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class FieldError {

        private String field;
        private String value;
        private String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? null : error.getRejectedValue().toString(),
                            Objects.equals(error.getCode(), "typeMismatch")
                                    ? ClientErrorCode.MISMATCH_TYPE.getMessage()
                                    : error.getDefaultMessage()
                    ))
                    .collect(Collectors.toList());
        }
    }
}

package com.swm.lito.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> applicationException(ApplicationException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindException(BindException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.fromBindException(e.getBindingResult()));
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> missingServletRequestPartException(MultipartException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.fromFileEmptyException());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.fromExceededSizeException());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> serverException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body(ErrorResponse.fromServerException(e));
    }
}

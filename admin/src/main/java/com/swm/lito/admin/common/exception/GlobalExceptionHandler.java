package com.swm.lito.admin.common.exception;

import com.swm.lito.core.common.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> applicationException(ApplicationException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindException(BindException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.fromBindException(e.getBindingResult()));
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> missingServletRequestPartException(MultipartException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.fromEmptyFileException());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.fromExceededSizeException());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> missingServletRequestParameterException(MissingServletRequestParameterException e){
        return ResponseEntity.badRequest().body(ErrorResponse.fromEmptyQueryStringException());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse> missingPathVariableException(MissingPathVariableException e){
        return ResponseEntity.badRequest().body(ErrorResponse.fromEmptyPathVariableException());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> serverException(Exception e) {
        return ResponseEntity.internalServerError().body(ErrorResponse.fromServerException(e));
    }
}

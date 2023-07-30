package com.swm.lito.core.common.exception.file;

import com.swm.lito.core.common.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileErrorCode implements ErrorEnumCode {

    FILE_UPLOAD_FAIL("F001","파일 업로드에 실패했습니다."),
    FILE_EMPTY("F002","파일을 등록해주세요."),
    FILE_EXCEEDED_SIZE("F003","업로드 가능한 파일 용량을 초과했습니다.")
    ;


    private final String code;
    private final String message;
}

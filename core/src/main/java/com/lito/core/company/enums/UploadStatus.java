package com.lito.core.company.enums;

import com.lito.core.common.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.lito.core.common.exception.company.CompanyErrorCode.INVALID_UPLOAD;

@Getter
@AllArgsConstructor
public enum UploadStatus {

    SUCCESS("승인"),
    FAIL("거부"),
    PROCESS("심사중");

    private String name;

}

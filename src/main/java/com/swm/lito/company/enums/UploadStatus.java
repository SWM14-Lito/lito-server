package com.swm.lito.company.enums;

import com.swm.lito.common.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.swm.lito.common.exception.company.CompanyErrorCode.INVALID_UPLOAD;

@Getter
@AllArgsConstructor
public enum UploadStatus {

    SUCCESS("승인"),
    FAIL("거부"),
    PROCESS("심사중");


    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(UploadStatus::getName, UploadStatus::name)));

    public static UploadStatus toEnum(String uploadStatus){
        if(Arrays.stream(values()).noneMatch(u->u.name.equals(uploadStatus)))
            throw new ApplicationException(INVALID_UPLOAD);
        return valueOf(TYPES.get(uploadStatus));
    }
}

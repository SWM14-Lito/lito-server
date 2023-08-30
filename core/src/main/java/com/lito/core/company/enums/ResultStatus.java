package com.lito.core.company.enums;

import com.lito.core.common.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.lito.core.common.exception.company.CompanyErrorCode.INVALID_RESULT;


@Getter
@AllArgsConstructor
public enum ResultStatus {

    SUCCESS("합격"),
    FAIL("불합격"),
    PROCESS("진행중");

    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(ResultStatus::getName, ResultStatus::name)));

    public static ResultStatus toEnum(String resultStatus){
        if(Arrays.stream(values()).noneMatch(r->r.name.equals(resultStatus)))
            throw new ApplicationException(INVALID_RESULT);
        return valueOf(TYPES.get(resultStatus));
    }
}

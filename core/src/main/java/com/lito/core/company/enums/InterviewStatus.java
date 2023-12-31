package com.lito.core.company.enums;

import com.lito.core.common.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.lito.core.common.exception.company.CompanyErrorCode.INVALID_INTERVIEW;

@Getter
@AllArgsConstructor
public enum InterviewStatus {

    INTERN("인턴"),
    OPEN_RECRUITMENT("공개채용"),
    OCCATIONAL_RECRUITMENT("수시채용");

    private String name;

}

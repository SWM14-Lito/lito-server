package com.swm.lito.company.enums;

import com.swm.lito.common.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.swm.lito.common.exception.company.CompanyErrorCode.INVALID_INTERVIEW;

@Getter
@AllArgsConstructor
public enum InterviewStatus {

    INTERN("인턴"),
    OPEN_RECRUITMENT("공개채용"),
    OCCATIONAL_RECRUITMENT("수시채용");

    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(InterviewStatus::getName, InterviewStatus::name)));

    public static InterviewStatus toEnum(String interviewStatus){
        if(Arrays.stream(values()).noneMatch(i->i.name.equals(interviewStatus)))
            throw new ApplicationException(INVALID_INTERVIEW);
        return valueOf(TYPES.get(interviewStatus));
    }

}

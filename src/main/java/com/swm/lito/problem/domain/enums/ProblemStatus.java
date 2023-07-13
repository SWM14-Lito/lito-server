package com.swm.lito.problem.domain.enums;

import com.swm.lito.common.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.swm.lito.common.exception.problem.ProblemErrorCode.INVALID_PROBLEM;


@Getter
@AllArgsConstructor
public enum ProblemStatus {

    //DB에 저장되는 값
    SUCCESS("풀이완료"),
    PROCESS("풀이중"),
    //풀지않은 상태값을 표현하기 위한 비즈니스 로직 용도
    NOT_SEEN("풀지않음"),
    //문제목록 조회시 전체 문제 상태를 조회하기 위한 용도
    ALL("전체")
    ;

    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(ProblemStatus::getName, ProblemStatus::name)));

    public static ProblemStatus toEnum(String problemStatus){
        if(Arrays.stream(values()).noneMatch(p->p.name.equals(problemStatus)))
            throw new ApplicationException(INVALID_PROBLEM);
        return valueOf(TYPES.get(problemStatus));
    }
}

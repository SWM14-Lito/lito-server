package com.swm.lito.problem.domain.enums;

import com.swm.lito.common.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.swm.lito.common.exception.infrastructure.InfraErrorCode.INVALID_OAUTH;

@Getter
@AllArgsConstructor
public enum Category {

    //OS
    OS1("운영체제 개념"),
    OS2("프로세스관리"),
    OS3("메모리관리"),
    OS4("저장장치관리"),
    OS5("보호와 보안"),
    OS6("분산 시스템"),
    OS7("전용시스템"),
    OS8("사례연구"),

    //NETWORK
    N1("컴퓨터 네트워크와 인터넷"),
    N2("어플리케이션 계층"),
    N3("전송 계층"),
    N4("네트워크 계층-data plane"),
    N5("네트워크 계층-control plane"),
    N6("링크 계층과 LAN"),
    N7("무선 및 이동 통신 네트워크"),
    N8("컴퓨터 네트워크 보안"),
    N9("멀티미디어 네트워킹"),

    //DATABASE
    DB1("데이터베이스 기본 개념"),
    DB2("데이터베이스 관리 시스템"),
    DB3("데이터베이스 시스템"),
    DB4("데이터 모델링"),
    DB5("관계 데이터 모델"),
    DB6("관계 데이터 연산"),
    DB7("데이터베이스 언어 SQL"),
    DB8("데이터베이스 설계"),
    DB9("정규화"),
    DB10("회복과 병행 제어"),
    DB11("보안과 권한 관리"),
    DB12("데이터베이스 응용 기술"),
    DB13("데이터 과학과 빅데이터"),

    //Data Structure
    DS1("자료구조 개념"),
    DS2("재귀"),
    DS3("배열"),
    DS4("리스트"),
    DS5("스택"),
    DS6("큐"),
    DS7("트리"),
    DS8("우선 순위 큐"),
    DS9("정렬"),
    DS10("그래프"),
    DS11("해싱"),
    DS12("탐색"),
    DS13("알고리즘");

    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Category::getName, Category::name)));

    public static Category toEnum(String category){
        if(Arrays.stream(values()).noneMatch(c->c.name.equals(category)))
            throw new ApplicationException(INVALID_OAUTH);
        return valueOf(TYPES.get(category));
    }
}

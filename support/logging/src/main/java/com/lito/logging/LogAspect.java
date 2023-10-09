package com.lito.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Aspect
@Slf4j
public class LogAspect {

    @Around("execution(* com.lito..*Controller.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable{
        Map<String, Object> data = getRequestMap(pjp);
        
        long startAt = System.currentTimeMillis();
        log.info("----------> Request: {}", data);

        Object result = pjp.proceed(pjp.getArgs());

        long endAt = System.currentTimeMillis();

        log.info("----------> RESPONSE : {}({}) => {} ({}ms)", pjp.getSignature().getDeclaringTypeName(),
                pjp.getSignature().getName(), result, endAt - startAt);

        return result;
    }

    private static Map<String, Object> getRequestMap(ProceedingJoinPoint pjp) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();


        Map<String, Object> data = new HashMap<>();

        data.put("host", request.getRemoteHost());
        data.put("request-method", request.getMethod());
        data.put("request-uri", request.getRequestURI());
        data.put("request-query_params", request.getQueryString());
        data.put("request-headers", request.getHeader("Authorization"));
        data.put("method-name", pjp.getSignature().getName());
        data.put("request-body",  !request.getMethod().equals("GET") ? getRequestBody(pjp) : "");

        return data;
    }

    private static String getRequestBody(ProceedingJoinPoint pjp){
        return Arrays.stream(pjp.getArgs())
                .filter(arg -> !(arg instanceof UserDetails))
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

}

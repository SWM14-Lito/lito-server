package com.lito.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Aspect
@Slf4j
public class LogAspect {

    @Around("execution(* com.lito..*Controller.*(..)) && args(.., @RequestBody body)")
    public Object logging(ProceedingJoinPoint pjp, Object body) throws Throwable{
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();

        data.put("host", request.getRemoteHost());
        data.put("request-method", request.getMethod());
        data.put("request-uri", request.getRequestURI());
        data.put("request-query_params", request.getQueryString());
        data.put("request-headers", request.getHeader("Authorization"));
        data.put("method-name", pjp.getSignature().getName());

        if (!request.getMethod().equals("GET") && body != null) {
            data.put("request-body", objectMapper.writeValueAsString(body));
        }

        long startAt = System.currentTimeMillis();
        log.info("----------> Request: {}", data);

        Object result = pjp.proceed();

        long endAt = System.currentTimeMillis();

        log.info("----------> RESPONSE : {}({}) => {} ({}ms)", pjp.getSignature().getDeclaringTypeName(),
                pjp.getSignature().getName(), result, endAt - startAt);

        return result;
    }

}

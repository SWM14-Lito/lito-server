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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;



@Component
@Aspect
@Slf4j
public class LogAspect {

    @Around("execution(* com.lito..*Controller.*(..)) && args(.., @RequestBody body) && args(.., @RequestPart file)")
    public Object logging(ProceedingJoinPoint pjp, Object body, MultipartFile file) throws Throwable{
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

        if (!request.getMethod().equals("GET") && body != null && file == null) {
            data.put("request-body", objectMapper.writeValueAsString(body));
        }

        if(file!=null){
            data.put("request-part", file.getOriginalFilename());
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

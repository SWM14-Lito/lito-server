package com.lito.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponse = new ContentCachingResponseWrapper(response);
        String uuid = UUID.randomUUID().toString();

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(cachingRequest, cachingResponse);
        long endTime = System.currentTimeMillis();

        Map<String, Object> data = new HashMap<>();

        data.put("host", getClientIp(request));
        data.put("request-method", request.getMethod());
        data.put("request-uri", request.getRequestURI());
        data.put("request-query_params", request.getQueryString());
        data.put("request-headers", request.getHeader("Authorization"));

        String requestBody = new String(cachingRequest.getContentAsByteArray());
        log.info("[traceId: {}] ----------> Request : {}, requestBody : {}", uuid, data, requestBody);

        String resContent = new String(cachingResponse.getContentAsByteArray());
        int httpStatus = cachingResponse.getStatus();
        cachingResponse.copyBodyToResponse();

        log.info("[traceId: {}] ----------> Response : response status : {}, responseBody : {}, {}ms", uuid, httpStatus, resContent, endTime - startTime);
    }

    private static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(ip==null)    ip = request.getRemoteAddr();
        return ip;
    }
}

package com.swm.lito.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.ErrorEnumCode;
import com.swm.lito.common.exception.ErrorResponse;
import com.swm.lito.common.exception.auth.AuthErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch(ApplicationException e){
            if(e.getErrorEnumCode().equals(AuthErrorCode.EXPIRED)){
                setResponse(response, AuthErrorCode.EXPIRED);
            }
            else if(e.getErrorEnumCode().equals(AuthErrorCode.INVALID_SIGNATURE)){
                setResponse(response, AuthErrorCode.INVALID_SIGNATURE);
            }
            else if(e.getErrorEnumCode().equals(AuthErrorCode.INVALID_JWT)){
                setResponse(response, AuthErrorCode.INVALID_JWT);
            }
            else if(e.getErrorEnumCode().equals(AuthErrorCode.UNSUPPORTED)){
                setResponse(response, AuthErrorCode.UNSUPPORTED);
            }
            else if(e.getErrorEnumCode().equals(AuthErrorCode.EMPTY_CLAIM)){
                setResponse(response, AuthErrorCode.EMPTY_CLAIM);
            }
        }
    }

    private void setResponse(HttpServletResponse response, ErrorEnumCode errorEnumCode) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String json = objectMapper.writeValueAsString(ErrorResponse.fromJwtExceptionFilter(errorEnumCode));
        response.getWriter().write(json);
    }
}

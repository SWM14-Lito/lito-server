package com.swm.lito.support.security;

import com.swm.lito.auth.adapter.out.persistence.TokenAdapter;
import com.swm.lito.auth.application.port.out.TokenCommandPort;
import com.swm.lito.auth.application.port.out.TokenQueryPort;
import com.swm.lito.common.security.CustomUserDetailsService;
import com.swm.lito.common.security.jwt.JwtAccessDeniedHandler;
import com.swm.lito.common.security.jwt.JwtAuthenticationEntryPoint;
import com.swm.lito.common.security.jwt.JwtProvider;
import com.swm.lito.user.application.port.out.UserQueryPort;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class SecuritySupport {

    @MockBean
    protected JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    protected JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @MockBean
    protected CustomUserDetailsService customUserDetailsService;
    @MockBean
    protected JwtProvider jwtProvider;
    @MockBean
    protected TokenCommandPort tokenCommandPort;
    @MockBean
    protected TokenQueryPort tokenQueryPort;
}

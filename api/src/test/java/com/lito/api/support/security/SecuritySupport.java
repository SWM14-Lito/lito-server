package com.lito.api.support.security;

import com.lito.core.auth.application.port.out.TokenCommandPort;
import com.lito.core.auth.application.port.out.TokenQueryPort;
import com.lito.api.common.security.CustomUserDetailsService;
import com.lito.api.common.security.jwt.JwtAccessDeniedHandler;
import com.lito.api.common.security.jwt.JwtAuthenticationEntryPoint;
import com.lito.core.common.security.jwt.JwtProvider;
import com.lito.api.config.SecurityConfig;
import com.lito.core.user.application.port.out.UserQueryPort;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import(SecurityConfig.class)
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
    @MockBean
    protected UserQueryPort userQueryPort;
}

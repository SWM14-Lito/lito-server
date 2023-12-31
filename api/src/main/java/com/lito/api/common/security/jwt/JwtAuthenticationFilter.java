package com.lito.api.common.security.jwt;

import com.lito.core.auth.application.port.out.TokenQueryPort;
import com.lito.core.common.security.CustomUserDetailsService;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.security.jwt.JwtProvider;
import com.lito.core.user.domain.enums.Provider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.lito.core.common.exception.auth.AuthErrorCode.UNAUTHORIZED;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtProvider jwtProvider;
    private final TokenQueryPort tokenQueryPort;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            String jwt = resolveToken(request);
            if (isValid(jwt)) {
                String email = jwtProvider.getUserEmail(jwt);
                Provider provider = Provider.toEnum(jwtProvider.getUserProvider(jwt));
                customUserDetailsService.setProvider(provider);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (ApplicationException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApplicationException(UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && jwtProvider.isStartWithBearer(bearerToken)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isValid(String jwt) {
        return StringUtils.hasText(jwt)
                && jwtProvider.validateToken(jwt)
                && !tokenQueryPort.existsLogoutAccessTokenById(jwt)
                && !tokenQueryPort.existsLogoutRefreshTokenById(jwt);
    }
}

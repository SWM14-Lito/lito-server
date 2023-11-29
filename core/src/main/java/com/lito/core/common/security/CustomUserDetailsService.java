package com.lito.core.common.security;

import com.lito.core.auth.application.port.out.AuthQueryPort;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.lito.core.common.exception.user.UserErrorCode.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthQueryPort authQueryPort;
    private Provider provider;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = authQueryPort.findByEmailAndProvider(email, provider)
                .orElseThrow(() -> new ApplicationException(USER_NOT_FOUND));
        return AuthUser.of(user);
    }

    public void setProvider(Provider provider){
        this.provider = provider;
    }
}

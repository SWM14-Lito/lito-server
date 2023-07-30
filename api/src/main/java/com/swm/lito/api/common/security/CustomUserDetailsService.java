package com.swm.lito.api.common.security;

import com.swm.lito.api.common.exception.ApplicationException;
import com.swm.lito.core.common.security.AuthUser;
import com.swm.lito.core.user.application.port.out.UserQueryPort;
import com.swm.lito.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.swm.lito.core.common.exception.user.UserErrorCode.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserQueryPort userQueryPort;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userQueryPort.findByEmail(email)
                .orElseThrow(() -> new ApplicationException(USER_NOT_FOUND));
        return AuthUser.of(user);
    }
}

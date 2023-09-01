package com.lito.core.auth.application.port.out;

import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;

import java.util.Optional;

public interface AuthQueryPort {

    Optional<User> findByOauthIdAndProvider(String oauthId, Provider provider);

    Optional<User> findByEmailAndProvider(String email, Provider provider);

}

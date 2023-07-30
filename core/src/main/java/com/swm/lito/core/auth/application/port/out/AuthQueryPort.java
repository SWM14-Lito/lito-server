package com.swm.lito.core.auth.application.port.out;

import com.swm.lito.core.user.domain.User;
import com.swm.lito.core.user.domain.enums.Provider;

import java.util.Optional;

public interface AuthQueryPort {

    Optional<User> findByOauthIdAndProvider(String oauthId, Provider provider);

}

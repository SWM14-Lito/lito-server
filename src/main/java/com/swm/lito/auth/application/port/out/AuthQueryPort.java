package com.swm.lito.auth.application.port.out;

import com.swm.lito.user.domain.User;
import com.swm.lito.user.domain.enums.Provider;

import java.util.Optional;

public interface AuthQueryPort {

    Optional<User> findByOauthIdAndProvider(String oauthId, Provider provider);

}

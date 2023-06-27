package com.swm.lito.auth.application.port.out;

import com.swm.lito.user.domain.User;

import java.util.Optional;

public interface AuthQueryPort {

    Optional<User> findByEmail(String email);

}

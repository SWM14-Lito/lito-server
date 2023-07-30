package com.swm.lito.core.user.application.port.out;

import com.swm.lito.core.user.domain.User;

import java.util.Optional;

public interface UserQueryPort {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);

    Optional<User> findById(Long id);

}

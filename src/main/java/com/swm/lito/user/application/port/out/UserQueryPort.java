package com.swm.lito.user.application.port.out;

import com.swm.lito.user.domain.User;

import java.util.Optional;

public interface UserQueryPort {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);

}

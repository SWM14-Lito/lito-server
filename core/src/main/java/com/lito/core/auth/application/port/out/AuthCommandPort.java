package com.lito.core.auth.application.port.out;

import com.lito.core.user.domain.User;

public interface AuthCommandPort {

    User save(User user);
}

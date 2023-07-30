package com.swm.lito.core.auth.application.port.out;

import com.swm.lito.core.user.domain.User;

public interface AuthCommandPort {

    User save(User user);
}

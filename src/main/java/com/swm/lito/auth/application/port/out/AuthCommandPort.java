package com.swm.lito.auth.application.port.out;

import com.swm.lito.user.domain.User;

public interface AuthCommandPort {

    User save(User user);
}

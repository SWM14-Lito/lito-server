package com.swm.lito.user.application.port.in;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.user.adapter.in.request.UserRequest;

public interface UserUseCase {

    void update(AuthUser authUser, UserRequest userRequest);
}

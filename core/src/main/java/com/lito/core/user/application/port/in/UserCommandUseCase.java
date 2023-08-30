package com.lito.core.user.application.port.in;

import com.lito.core.common.security.AuthUser;
import com.lito.core.user.application.port.in.request.ProfileRequestDto;
import com.lito.core.user.application.port.in.request.UserRequestDto;

public interface UserCommandUseCase {

    void create(AuthUser authUser, UserRequestDto userRequestDto);
    void update(AuthUser authUser, ProfileRequestDto profileRequestDto);
    void updateNotification(AuthUser authUser, String alarmStatus);

    void delete(AuthUser authUser);
}

package com.swm.lito.user.application.port.in;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.user.application.port.in.request.UserRequestDto;

public interface UserCommandUseCase {

    void update(AuthUser authUser, UserRequestDto userRequestDto);
    void updateNotification(AuthUser authUser, String alarmStatus);
}

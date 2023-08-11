package com.swm.lito.core.user.application.port.in;

import com.swm.lito.core.common.security.AuthUser;
import com.swm.lito.core.user.application.port.in.request.UserRequestDto;

public interface UserCommandUseCase {

    void update(AuthUser authUser, UserRequestDto userRequestDto);
    void updateNotification(AuthUser authUser, String alarmStatus);

    void delete(AuthUser authUser);
}

package com.swm.lito.user.application.service;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.user.UserErrorCode;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.user.application.port.in.UserCommandUseCase;
import com.swm.lito.user.application.port.in.request.UserRequestDto;
import com.swm.lito.user.application.port.out.UserQueryPort;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandService implements UserCommandUseCase {

    private final UserQueryPort userQueryPort;

    @Override
    public void update(AuthUser authUser, UserRequestDto userRequestDto) {
        User user = userQueryPort.findById(authUser.getUserId())
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        userQueryPort.findByNickname(userRequestDto.getNickname())
                .ifPresent(findUser -> {
                    throw new ApplicationException(UserErrorCode.USER_NICKNAME_EXIST);
                });
        user.validateUser(authUser, user);
        user.change(userRequestDto);
    }

    @Override
    public void updateNotification(AuthUser authUser, String alarmStatus) {
        User user = userQueryPort.findById(authUser.getUserId())
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        user.changeNotification(alarmStatus);

    }
}

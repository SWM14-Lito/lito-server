package com.swm.lito.core.user.application.service;

import com.swm.lito.core.common.exception.ApplicationException;
import com.swm.lito.core.common.exception.user.UserErrorCode;
import com.swm.lito.core.common.security.AuthUser;
import com.swm.lito.core.user.application.port.in.UserCommandUseCase;
import com.swm.lito.core.user.application.port.in.request.ProfileRequestDto;
import com.swm.lito.core.user.application.port.in.request.UserRequestDto;
import com.swm.lito.core.user.application.port.out.UserQueryPort;
import com.swm.lito.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandService implements UserCommandUseCase {

    private final UserQueryPort userQueryPort;

    @Override
    public void create(AuthUser authUser, UserRequestDto userRequestDto){
        User user = userQueryPort.findById(authUser.getUserId())
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        userQueryPort.findByNickname(userRequestDto.getNickname())
                .ifPresent(findUser -> {
                    throw new ApplicationException(UserErrorCode.USER_EXISTED_NICKNAME);
                });
        user.create(userRequestDto);
    }

    @Override
    public void update(AuthUser authUser, ProfileRequestDto profileRequestDto) {
        User user = userQueryPort.findById(authUser.getUserId())
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        userQueryPort.findByNickname(profileRequestDto.getNickname())
                .ifPresent(findUser -> {
                    throw new ApplicationException(UserErrorCode.USER_EXISTED_NICKNAME);
                });
        user.change(profileRequestDto);
    }

    @Override
    public void updateNotification(AuthUser authUser, String alarmStatus) {
        User user = userQueryPort.findById(authUser.getUserId())
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        user.changeNotification(alarmStatus);

    }

    @Override
    public void delete(AuthUser authUser) {
        User user = userQueryPort.findById(authUser.getUserId())
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        user.deleteUser();
    }
}

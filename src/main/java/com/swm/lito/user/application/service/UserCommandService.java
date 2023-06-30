package com.swm.lito.user.application.service;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.user.UserErrorCode;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.user.adapter.in.request.UserRequest;
import com.swm.lito.user.application.port.in.UserCommandUseCase;
import com.swm.lito.user.application.port.out.UserQueryPort;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService implements UserCommandUseCase {

    private final UserQueryPort userQueryPort;

    @Override
    public void update(AuthUser authUser, UserRequest userRequest) {
        User user = userQueryPort.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        userQueryPort.findByNickname(userRequest.getNickname())
                .ifPresent(findUser -> {
                    throw new ApplicationException(UserErrorCode.USER_NICKNAME_EXIST);
                });
        user.validateUser(authUser, user);
        user.change(userRequest);
    }
}

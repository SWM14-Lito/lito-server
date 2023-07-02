package com.swm.lito.user.application.service;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.user.UserErrorCode;
import com.swm.lito.user.application.port.in.UserQueryUseCase;
import com.swm.lito.user.application.port.in.response.UserResponseDto;
import com.swm.lito.user.application.port.out.UserQueryPort;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService implements UserQueryUseCase {

    private final UserQueryPort userQueryPort;

    @Override
    public UserResponseDto find(Long id) {
        User user = userQueryPort.findById(id)
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        return UserResponseDto.from(user);
    }
}

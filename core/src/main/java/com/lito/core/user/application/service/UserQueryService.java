package com.lito.core.user.application.service;

import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.user.UserErrorCode;
import com.lito.core.user.application.port.out.UserQueryPort;
import com.lito.core.user.application.port.in.UserQueryUseCase;
import com.lito.core.user.application.port.in.response.UserResponseDto;
import com.lito.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService implements UserQueryUseCase {

    private final UserQueryPort userQueryPort;

    @Override
    public UserResponseDto find(Long id) {
        User user = userQueryPort.findById(id)
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        return UserResponseDto.from(user);
    }
}

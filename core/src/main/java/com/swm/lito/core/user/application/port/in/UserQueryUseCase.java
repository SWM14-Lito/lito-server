package com.swm.lito.core.user.application.port.in;

import com.swm.lito.core.user.application.port.in.response.UserResponseDto;

public interface UserQueryUseCase {

    UserResponseDto find(Long id);
}

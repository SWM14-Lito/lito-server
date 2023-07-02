package com.swm.lito.user.application.port.in;

import com.swm.lito.user.application.port.in.response.UserResponseDto;

public interface UserQueryUseCase {

    UserResponseDto find(Long id);
}

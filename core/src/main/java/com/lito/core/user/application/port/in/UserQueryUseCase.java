package com.lito.core.user.application.port.in;

import com.lito.core.user.application.port.in.response.UserResponseDto;
import com.lito.core.user.domain.User;

import java.util.List;

public interface UserQueryUseCase {

    UserResponseDto find(Long id);

    List<User> findAll();
}

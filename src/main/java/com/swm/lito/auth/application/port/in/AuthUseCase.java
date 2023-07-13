package com.swm.lito.auth.application.port.in;

import com.swm.lito.auth.application.port.in.request.LoginRequestDto;
import com.swm.lito.auth.application.port.in.response.LoginResponseDto;
import com.swm.lito.user.domain.enums.Provider;

public interface AuthUseCase {

    LoginResponseDto login(Provider provider, LoginRequestDto loginRequestDto);

    void logout(String accessToken, String refreshToken);
}

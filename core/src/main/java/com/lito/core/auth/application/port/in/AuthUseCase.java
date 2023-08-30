package com.lito.core.auth.application.port.in;

import com.lito.core.auth.application.port.in.request.LoginRequestDto;
import com.lito.core.auth.application.port.in.response.LoginResponseDto;
import com.lito.core.auth.application.port.in.response.ReissueTokenResponseDto;
import com.lito.core.common.security.AuthUser;

public interface AuthUseCase {

    LoginResponseDto login(String provider, LoginRequestDto loginRequestDto);

    void logout(String accessToken, String refreshToken);

    ReissueTokenResponseDto reissue(AuthUser authUser, String refreshToken);
}

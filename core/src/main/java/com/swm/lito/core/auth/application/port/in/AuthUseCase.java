package com.swm.lito.core.auth.application.port.in;

import com.swm.lito.core.auth.application.port.in.request.LoginRequestDto;
import com.swm.lito.core.auth.application.port.in.response.LoginResponseDto;
import com.swm.lito.core.auth.application.port.in.response.ReissueTokenResponseDto;
import com.swm.lito.core.common.security.AuthUser;
import com.swm.lito.core.user.domain.enums.Provider;

public interface AuthUseCase {

    LoginResponseDto login(String provider, LoginRequestDto loginRequestDto);

    void logout(String accessToken, String refreshToken);

    ReissueTokenResponseDto reissue(AuthUser authUser, String refreshToken);
}

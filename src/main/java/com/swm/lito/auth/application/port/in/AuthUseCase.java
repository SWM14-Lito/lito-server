package com.swm.lito.auth.application.port.in;

import com.swm.lito.auth.application.port.in.response.LoginResponseDto;

public interface AuthUseCase {

    LoginResponseDto login(String provider, String accessToken);
}

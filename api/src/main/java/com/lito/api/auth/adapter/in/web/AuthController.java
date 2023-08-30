package com.lito.api.auth.adapter.in.web;

import com.lito.api.auth.adapter.in.request.LoginRequest;
import com.lito.api.auth.adapter.in.response.LoginResponse;
import com.lito.api.auth.adapter.in.response.ReissueTokenResponse;
import com.lito.core.auth.application.port.in.AuthUseCase;
import com.lito.core.common.security.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/{provider}/login")
    public LoginResponse login(@PathVariable String provider,
                               @RequestBody @Valid LoginRequest loginRequest){
        return LoginResponse.from(authUseCase.login(provider,loginRequest.toRequestDto()));
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
                       @RequestHeader("Refresh-Token") String refreshToken){
        authUseCase.logout(accessToken.substring(7), refreshToken);
    }

    @PostMapping("/reissue")
    public ReissueTokenResponse reissue(@AuthenticationPrincipal AuthUser authUser,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken){
        return ReissueTokenResponse.from(authUseCase.reissue(authUser, refreshToken));
    }
}

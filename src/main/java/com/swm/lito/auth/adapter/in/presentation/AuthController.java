package com.swm.lito.auth.adapter.in.presentation;

import com.swm.lito.auth.adapter.in.response.LoginResponse;
import com.swm.lito.auth.application.port.in.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @GetMapping("/{provider}/login")
    public LoginResponse login(@PathVariable String provider,
                               @RequestHeader(value = "OauthAccessToken") String OauthAccessToken){
        return LoginResponse.from(authUseCase.login(provider, OauthAccessToken));

    }
}

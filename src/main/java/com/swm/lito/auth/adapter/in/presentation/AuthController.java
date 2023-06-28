package com.swm.lito.auth.adapter.in.presentation;

import com.swm.lito.auth.application.port.in.AuthUseCase;
import com.swm.lito.auth.application.port.in.response.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @GetMapping("/{provider}/login")
    public LoginResponseDto login(@PathVariable String provider,
                                  @RequestHeader(value = "OauthAccessToken") String OauthAccessToken){
        return authUseCase.login(provider, OauthAccessToken);

    }
}

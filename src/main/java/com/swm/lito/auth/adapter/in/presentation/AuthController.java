package com.swm.lito.auth.adapter.in.presentation;

import com.swm.lito.auth.adapter.in.request.LoginRequest;
import com.swm.lito.auth.adapter.in.response.LoginResponse;
import com.swm.lito.auth.application.port.in.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.swm.lito.user.domain.enums.Provider.toEnum;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/{provider}/login")
    public LoginResponse login(@PathVariable String provider,
                               @RequestBody LoginRequest loginRequest){
        return LoginResponse.from(authUseCase.login(toEnum(provider),loginRequest.toRequestDto()));

    }
}

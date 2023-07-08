package com.swm.lito.auth.adapter.in.presentation;

import com.swm.lito.auth.adapter.in.request.LoginRequest;
import com.swm.lito.auth.adapter.in.response.LoginResponse;
import com.swm.lito.auth.application.port.in.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.swm.lito.user.domain.enums.Provider.toEnum;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/{provider}/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@PathVariable String provider,
                               @RequestBody @Valid LoginRequest loginRequest){
        return LoginResponse.from(authUseCase.login(toEnum(provider),loginRequest.toRequestDto()));

    }
}

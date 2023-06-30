package com.swm.lito.user.adapter.in.presentation;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.user.adapter.in.request.UserRequest;
import com.swm.lito.user.application.port.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    @PatchMapping
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @RequestBody UserRequest userRequest){
        userUseCase.update(authUser, userRequest);
    }
}

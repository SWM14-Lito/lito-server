package com.swm.lito.api.user.adapter.in.web;

import com.swm.lito.core.common.security.AuthUser;
import com.swm.lito.api.user.adapter.in.request.UserRequest;
import com.swm.lito.api.user.adapter.in.response.UserResponse;
import com.swm.lito.core.user.application.port.in.UserCommandUseCase;
import com.swm.lito.core.user.application.port.in.UserQueryUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCommandUseCase userCommandUseCase;
    private final UserQueryUseCase userQueryUseCase;

    @GetMapping("/{id}")
    public UserResponse find(@PathVariable Long id){
        return UserResponse.from(userQueryUseCase.find(id));
    }

    @PatchMapping
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @RequestBody @Valid UserRequest userRequest){
        userCommandUseCase.update(authUser, userRequest.toRequestDto());
    }
    @PatchMapping("/notifications")
    public void updateNotification(@AuthenticationPrincipal AuthUser authUser,
                                   @RequestParam String alarmStatus){
        userCommandUseCase.updateNotification(authUser,alarmStatus);
    }

    @DeleteMapping
    public void delete(@AuthenticationPrincipal AuthUser authUser){
        userCommandUseCase.delete(authUser);
    }
}

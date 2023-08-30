package com.lito.api.user.adapter.in.web;

import com.lito.api.user.adapter.in.request.ProfileRequest;
import com.lito.api.user.adapter.in.response.UserResponse;
import com.lito.api.user.adapter.in.request.UserRequest;
import com.lito.core.common.security.AuthUser;
import com.lito.core.user.application.port.in.UserCommandUseCase;
import com.lito.core.user.application.port.in.UserQueryUseCase;
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

    @PostMapping
    public void create(@AuthenticationPrincipal AuthUser authUser,
                       @RequestBody @Valid UserRequest userRequest){
        userCommandUseCase.create(authUser, userRequest.toRequestDto());
    }

    @PatchMapping
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @RequestBody @Valid ProfileRequest profileRequest){
        userCommandUseCase.update(authUser, profileRequest.toRequestDto());
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

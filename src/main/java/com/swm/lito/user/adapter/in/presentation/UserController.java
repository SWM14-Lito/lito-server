package com.swm.lito.user.adapter.in.presentation;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.user.adapter.in.request.UserRequest;
import com.swm.lito.user.adapter.in.response.UserResponse;
import com.swm.lito.user.application.port.in.UserCommandUseCase;
import com.swm.lito.user.application.port.in.UserQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCommandUseCase userCommandUseCase;
    private final UserQueryUseCase userQueryUseCase;

    @GetMapping("/{id}")
    public UserResponse find(@PathVariable Long id){
        return UserResponse.from(userQueryUseCase.find(id));
    }

    @PatchMapping(consumes = { "multipart/form-data"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @RequestPart("userRequest") UserRequest userRequest,
                       @RequestPart(value = "file", required = false) MultipartFile file){
        userCommandUseCase.update(authUser, userRequest.toRequestDto(), file);
    }
    @PatchMapping("/notification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNotification(@AuthenticationPrincipal AuthUser authUser,
                                   @RequestParam String alarmStatus){
        userCommandUseCase.updateNotification(authUser,alarmStatus);
    }
}

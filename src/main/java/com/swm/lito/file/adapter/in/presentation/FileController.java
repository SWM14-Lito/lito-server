package com.swm.lito.file.adapter.in.presentation;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.file.application.port.in.FileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {

    private final FileUseCase fileUseCase;

    @PostMapping("/users/files")
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@AuthenticationPrincipal AuthUser authUser,
                       @RequestPart("file") MultipartFile file) {
        fileUseCase.upload(authUser, file);
    }
}

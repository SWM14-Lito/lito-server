package com.swm.lito.file.adapter.in.presentation;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.file.application.port.in.FileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FileController {

    private final FileUseCase fileUseCase;

    @PostMapping("/users/files")
    public void upload(@AuthenticationPrincipal AuthUser authUser,
                       @RequestPart("file") MultipartFile file) {
        fileUseCase.upload(authUser, file);
    }
}

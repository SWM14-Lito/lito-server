package com.lito.core.file.application.service;

import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.user.UserErrorCode;
import com.lito.core.common.security.AuthUser;
import com.lito.core.file.application.port.in.FileUseCase;
import com.lito.core.file.application.port.out.FilePort;
import com.lito.core.user.application.port.out.UserQueryPort;
import com.lito.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Transactional
public class FileService implements FileUseCase {

    private final FilePort filePort;
    private final UserQueryPort userQueryPort;


    @Override
    public void upload(AuthUser authUser, MultipartFile multipartFile) {
        User user = userQueryPort.findById(authUser.getUserId())
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        filePort.delete(user.getProfileImgUrl());
        String fileUrl = filePort.upload(multipartFile);
        user.changeProfileImgUrl(fileUrl);
    }
}

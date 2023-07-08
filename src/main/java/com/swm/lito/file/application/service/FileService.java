package com.swm.lito.file.application.service;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.user.UserErrorCode;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.file.application.port.in.FileUseCase;
import com.swm.lito.file.application.port.out.FilePort;
import com.swm.lito.user.application.port.out.UserQueryPort;
import com.swm.lito.user.domain.User;
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

package com.swm.lito.user.application.service;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.user.UserErrorCode;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.common.storage.StoragePort;
import com.swm.lito.user.application.port.in.UserCommandUseCase;
import com.swm.lito.user.application.port.in.request.UserRequestDto;
import com.swm.lito.user.application.port.out.UserQueryPort;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandService implements UserCommandUseCase {

    private final UserQueryPort userQueryPort;
    private final StoragePort storagePort;

    @Override
    public void update(AuthUser authUser, UserRequestDto userRequestDto, MultipartFile multipartFile) {
        User user = userQueryPort.findById(authUser.getUserId())
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        String imageUrl = Optional.ofNullable(multipartFile)
                .map(img -> {
                    storagePort.delete(user.getProfileImgUrl());
                    return storagePort.upload(img);
                })
                .orElse("");
        userQueryPort.findByNickname(userRequestDto.getNickname())
                .ifPresent(findUser -> {
                    throw new ApplicationException(UserErrorCode.USER_NICKNAME_EXIST);
                });
        user.validateUser(authUser, user);
        user.change(userRequestDto,imageUrl);
    }

    @Override
    public void updateNotification(AuthUser authUser, String alarmStatus) {
        User user = userQueryPort.findById(authUser.getUserId())
                .orElseThrow(() -> new ApplicationException(UserErrorCode.USER_NOT_FOUND));
        user.changeNotification(alarmStatus);

    }
}

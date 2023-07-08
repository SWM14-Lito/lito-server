package com.swm.lito.file.application.port.in;

import com.swm.lito.common.security.AuthUser;
import org.springframework.web.multipart.MultipartFile;

public interface FileUseCase {

    void upload(AuthUser authUser, MultipartFile multipartFile);
}

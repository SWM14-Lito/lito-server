package com.swm.lito.core.file.application.port.in;

import com.swm.lito.core.common.security.AuthUser;
import org.springframework.web.multipart.MultipartFile;

public interface FileUseCase {

    void upload(AuthUser authUser, MultipartFile multipartFile);
}

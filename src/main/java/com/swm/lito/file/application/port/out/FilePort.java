package com.swm.lito.file.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface FilePort {

    String upload(MultipartFile multipartFile);
    void delete(String imageUrl);
}

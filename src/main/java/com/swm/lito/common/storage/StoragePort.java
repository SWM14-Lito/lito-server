package com.swm.lito.common.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StoragePort {

    String upload(MultipartFile multipartFile);
    void delete(String imageUrl);
}

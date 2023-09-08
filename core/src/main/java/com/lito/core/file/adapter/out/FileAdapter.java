package com.lito.core.file.adapter.out;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.file.FileErrorCode;
import com.lito.core.file.application.port.out.FilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class FileAdapter implements FilePort {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.file-path}")
    private String filePath;

    @Override
    public String upload(MultipartFile multipartFile) {
        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()){
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
        } catch(IOException e){
            throw new ApplicationException(FileErrorCode.FILE_UPLOAD_FAIL);
        }
        return String.valueOf(amazonS3Client.getUrl(bucket, fileName));
    }

    private String createFileName(String fileName) {
        String dirName= LocalDate.now().toString();
        return dirName + "/" + UUID.randomUUID() + fileName;
    }

    @Override
    public void delete(String fileUrl) {
        if (!StringUtils.hasText(fileUrl)) {
            return;
        }
        amazonS3Client.deleteObject(bucket, fileUrl.replace(filePath,""));
    }

}

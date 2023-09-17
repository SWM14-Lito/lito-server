package com.lito.core.file.adapter.out;

import com.amazonaws.services.s3.AmazonS3Client;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.file.FileErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("FileAdapter")
class FileAdapterTest {

    @MockBean
    AmazonS3Client amazonS3Client;

    @Autowired
    FileAdapter fileAdapter;

    @Nested
    @DisplayName("upload 메서드는")
    class upload{

        MultipartFile multipartFile = mock(MultipartFile.class);

        @Nested
        @DisplayName("multipartFile을 가지고")
        class with_multipartfile{

            @Test
            @DisplayName("s3에 저장된 url을 리턴한다.")
            void it_returns_image_url() throws Exception{
                URL s3ImageUrl = new URL("https://bucket/filePath");
                given(amazonS3Client.getUrl(any(),any()))
                        .willReturn(s3ImageUrl);

                String url = fileAdapter.upload(multipartFile);

                assertThat(url).isEqualTo(s3ImageUrl.toString());
            }
        }

        @Nested
        @DisplayName("만약 s3에 put 요청하는 과정에 에러가 발생하면")
        class with_error_to_put_s3{

            @Test
            @DisplayName("FILE_UPLOAD_FAIL 예외를 발생시킨다.")
            void it_throws_file_upload_fail() throws Exception{

                when(multipartFile.getInputStream()).thenThrow(new IOException("Test IOException"));


                assertThatThrownBy(() -> fileAdapter.upload(multipartFile))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(FileErrorCode.FILE_UPLOAD_FAIL.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class delete{

        @Nested
        @DisplayName("fileUrl을 가지고")
        class with_file_url{

            String fileUrl = "fileUrl";

            @Test
            @DisplayName("s3에 저장된 파일을 삭제한다.")
            void it_deletes_in_s3() throws Exception{

                assertThatCode(() -> fileAdapter.delete(fileUrl))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("만약 fileUrl의 값이 공백,빈 값, null이라면")
        class with_file_url_white_space_or_no_value_or_null{

            String fileUrl = "";

            @Test
            @DisplayName("메서드를 종료한다.")
            void it_quits_delete_method() throws Exception{

                assertThatCode(() -> fileAdapter.delete(fileUrl))
                        .doesNotThrowAnyException();
            }
        }
    }
}
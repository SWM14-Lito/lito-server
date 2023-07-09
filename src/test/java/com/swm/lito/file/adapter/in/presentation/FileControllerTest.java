package com.swm.lito.file.adapter.in.presentation;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.file.FileErrorCode;
import com.swm.lito.file.application.port.in.FileUseCase;
import com.swm.lito.support.restdocs.RestDocsSupport;
import com.swm.lito.support.security.WithMockJwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;

import static com.swm.lito.support.restdocs.RestDocsConfig.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockJwt
class FileControllerTest extends RestDocsSupport {

    @MockBean
    private FileUseCase fileUseCase;

    @Test
    @DisplayName("파일 업로드 성공")
    void file_upload_success() throws Exception {

        //given
        MockMultipartFile file = new MockMultipartFile("file", "image.png","image/png","file".getBytes());
        //when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/users/files")
                        .file(file)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );
        //then
        resultActions
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                    requestHeaders(
                            headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                    ),
                    requestParts(
                        partWithName("file").description("업로드할 파일")
                    )
                ));
    }

    @Test
    @DisplayName("파일 업로드 실패")
    void file_upload_fail() throws Exception {

        //given
        MockMultipartFile file = new MockMultipartFile("file", "image.png","image/png","file".getBytes());
        willThrow(new ApplicationException(FileErrorCode.FILE_UPLOAD_FAIL))
                .given(fileUseCase).upload(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/users/files")
                        .file(file)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(FileErrorCode.FILE_UPLOAD_FAIL.getCode())))
                .andExpect(jsonPath("$.message",is(FileErrorCode.FILE_UPLOAD_FAIL.getMessage())));
    }

    @Test
    @DisplayName("파일 업로드 실패 / 파일을 전송하지 않을 경우")
    void file_upload_fail_not_empty() throws Exception {

        //given
        MockMultipartFile file = new MockMultipartFile("file", "image.png","image/png","file".getBytes());
        willThrow(new ApplicationException(FileErrorCode.FILE_EMPTY))
                .given(fileUseCase).upload(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/users/files")
                        .file(file)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(FileErrorCode.FILE_EMPTY.getCode())))
                .andExpect(jsonPath("$.message",is(FileErrorCode.FILE_EMPTY.getMessage())));
    }

    @Test
    @DisplayName("파일 업로드 실패 / 파일 용량 초과")
    void file_upload_fail_exceeded_size() throws Exception {

        //given
        MockMultipartFile file = new MockMultipartFile("file", "image.png","image/png","file".getBytes());
        willThrow(new ApplicationException(FileErrorCode.FILE_EXCEEDED_SIZE))
                .given(fileUseCase).upload(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/users/files")
                        .file(file)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(FileErrorCode.FILE_EXCEEDED_SIZE.getCode())))
                .andExpect(jsonPath("$.message",is(FileErrorCode.FILE_EXCEEDED_SIZE.getMessage())));
    }

}
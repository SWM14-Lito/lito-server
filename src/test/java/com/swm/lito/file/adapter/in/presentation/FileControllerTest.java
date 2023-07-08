package com.swm.lito.file.adapter.in.presentation;

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
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
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

}
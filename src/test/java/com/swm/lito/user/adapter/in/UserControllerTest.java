package com.swm.lito.user.adapter.in;

import com.swm.lito.support.restdocs.RestDocsSupport;
import com.swm.lito.support.security.WithMockJwt;
import com.swm.lito.user.adapter.in.presentation.UserController;
import com.swm.lito.user.adapter.in.request.UserRequest;
import com.swm.lito.user.application.port.in.UserCommandUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.swm.lito.support.restdocs.RestDocsConfig.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockJwt
public class UserControllerTest extends RestDocsSupport {

    @MockBean
    private UserCommandUseCase userCommandUseCase;

    @Test
    @DisplayName("유저 프로필 수정 성공")
    void update_user_success() throws Exception {

        //given
        UserRequest request = UserRequest.builder()
                .nickname("수정")
                .profileImgUrl("수정")
                .introduce("수정")
                .build();
        willDoNothing().given(userCommandUseCase).update(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/users")
                .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).optional().description("변경할 닉네임, 변경하지 않을 경우 기존 닉네임 입력"),
                                fieldWithPath("profileImgUrl").type(JsonFieldType.STRING).optional().description("변경할 프로필 사진 URL, 변경하지 않을 경우 기존 프로필 이미지 URL 입력"),
                                fieldWithPath("introduce").type(JsonFieldType.STRING).optional().description("변경할 유저 소개, 변경하지 않을 경우 기존 소개 입력")
                        )
                ));
    }
}

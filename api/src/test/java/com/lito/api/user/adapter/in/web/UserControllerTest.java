package com.lito.api.user.adapter.in.web;

import com.lito.api.support.restdocs.RestDocsSupport;
import com.lito.api.support.security.WithMockJwt;
import com.lito.api.user.adapter.in.request.ProfileRequest;
import com.lito.api.user.adapter.in.request.UserRequest;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.user.application.port.in.UserCommandUseCase;
import com.lito.core.user.application.port.in.UserQueryUseCase;
import com.lito.core.user.application.port.in.response.UserResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.lito.api.support.restdocs.RestDocsConfig.field;
import static com.lito.core.common.exception.user.UserErrorCode.USER_EXISTED_NICKNAME;
import static com.lito.core.common.exception.user.UserErrorCode.USER_NOT_FOUND;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockJwt
public class UserControllerTest extends RestDocsSupport {

    @MockBean
    private UserCommandUseCase userCommandUseCase;

    @MockBean
    private UserQueryUseCase userQueryUseCase;

    @Test
    @DisplayName("유저 조회 성공")
    void find_user_success() throws Exception {

        //given
        UserResponseDto responseDto = UserResponseDto.builder()
                .userId(1L)
                .profileImgUrl("프로필 이미지")
                .point(0)
                .nickname("닉네임")
                .introduce("자기소개")
                .alarmStatus("Y")
                .build();
        given(userQueryUseCase.find(any()))
                .willReturn(responseDto);
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/users/{id}",1L)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",is(1)))
                .andExpect(jsonPath("$.profileImgUrl",is("프로필 이미지")))
                .andExpect(jsonPath("$.point",is(0)))
                .andExpect(jsonPath("$.nickname",is("닉네임")))
                .andExpect(jsonPath("$.introduce",is("자기소개")))
                .andExpect(jsonPath("$.alarmStatus",is("Y")))
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        pathParameters(
                                parameterWithName("id").description("유저 id")
                        ),
                        responseFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 고유 id"),
                                fieldWithPath("profileImgUrl").type(JsonFieldType.STRING).description("유저 프로필 이미지 URL"),
                                fieldWithPath("point").type(JsonFieldType.NUMBER).description("유저 포인트"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                fieldWithPath("introduce").type(JsonFieldType.STRING).description("유저 소개"),
                                fieldWithPath("alarmStatus").type(JsonFieldType.STRING).description("유저 알림 수신 여부")
                        )
                ));
    }

    @Test
    @DisplayName("유저 조회 실패 / 존재하지 않는 유저")
    void find_user_fail_not_found() throws Exception {

        //given
        given(userQueryUseCase.find(any()))
                .willThrow(new ApplicationException(USER_NOT_FOUND));
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/users/{id}",1L)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(USER_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(USER_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName("프로필 생성 성공")
    void create_user_success() throws Exception {

        //given
        UserRequest request = UserRequest.builder()
                .nickname("닉네임")
                .introduce("소개")
                .build();
        willDoNothing().given(userCommandUseCase).create(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("introduce").type(JsonFieldType.STRING).optional().description("유저 소개")
                        )
                ));
    }

    @Test
    @DisplayName("프로필 생성 실패 / 존재하지 않는 유저")
    void create_user_fail_not_found_user() throws Exception {

        //given
        UserRequest request = UserRequest.builder()
                .nickname("닉네임")
                .introduce("소개")
                .build();
        willThrow(new ApplicationException(USER_NOT_FOUND))
                .given(userCommandUseCase).create(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(USER_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(USER_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName("프로필 생성 실패 / 이미 존재하는 닉네임")
    void create_user_fail_existed_nickname() throws Exception {

        //given
        UserRequest request = UserRequest.builder()
                .nickname("닉네임")
                .introduce("소개")
                .build();
        willThrow(new ApplicationException(USER_EXISTED_NICKNAME))
                .given(userCommandUseCase).create(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(USER_EXISTED_NICKNAME.getCode())))
                .andExpect(jsonPath("$.message",is(USER_EXISTED_NICKNAME.getMessage())));
    }

    @Test
    @DisplayName("프로필 생성 실패 / 입력 조건에 대한 예외")
    void create_user_fail_invalid_request() throws Exception {

        //given
        UserRequest request = UserRequest.builder()
                .nickname("")
                .introduce("")
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors",hasSize(2)));

    }

    @Test
    @DisplayName("프로필 수정 성공")
    void update_user_success() throws Exception {

        //given
        ProfileRequest request = ProfileRequest.builder()
                .nickname("닉네임")
                .introduce("소개")
                .build();
        willDoNothing().given(userCommandUseCase).update(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).optional().description("변경할 닉네임"),
                                fieldWithPath("introduce").type(JsonFieldType.STRING).optional().description("변경할 유저 소개")
                        )
                ));
    }

    @Test
    @DisplayName("프로필 수정 실패 / 존재하지 않는 유저")
    void update_user_fail_not_found() throws Exception {

        //given
        ProfileRequest request = ProfileRequest.builder()
                .nickname("닉네임")
                .introduce("소개")
                .build();
        willThrow(new ApplicationException(USER_NOT_FOUND))
                .given(userCommandUseCase).update(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(USER_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(USER_NOT_FOUND.getMessage())));
    }


    @Test
    @DisplayName("프로필 수정 실패 / 이미 존재하는 닉네임")
    void update_user_fail_existed_nickname() throws Exception {

        //given
        ProfileRequest request = ProfileRequest.builder()
                .nickname("닉네임")
                .introduce("소개")
                .build();
        willThrow(new ApplicationException(USER_EXISTED_NICKNAME))
                .given(userCommandUseCase).update(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(USER_EXISTED_NICKNAME.getCode())))
                .andExpect(jsonPath("$.message",is(USER_EXISTED_NICKNAME.getMessage())));
    }

    @Test
    @DisplayName("프로필 수정 실패 / 입력 조건에 대한 예외")
    void update_user_fail_invalid_request() throws Exception {

        // given
        ProfileRequest request = ProfileRequest.builder()
                .nickname("")
                .introduce("")
                .build();
        // when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));
        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors",hasSize(1)));
    }

    @Test
    @DisplayName("유저 알림 수신여부 성공")
    void update_notification_success() throws Exception {

        //given
        willDoNothing().given(userCommandUseCase).updateNotification(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/users/notifications")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .queryParam("alarmStatus","Y")

        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        queryParameters(
                                parameterWithName("alarmStatus").description("변경시키려는 알람 수신 여부 값(Y/N)")
                        )


                ));
    }

    @Test
    @DisplayName("유저 알림 수신여부 실패 / 존재하지 않는 유저")
    void update_notification_fail_not_found() throws Exception {

        //given
        willThrow(new ApplicationException(USER_NOT_FOUND))
                .given(userCommandUseCase).updateNotification(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/users/notifications")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .queryParam("alarmStatus","Y")

        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(USER_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(USER_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName("유저 삭제 성공")
    void delete_user_success() throws Exception {

        // given
        willDoNothing().given(userCommandUseCase).delete(any());
        // when
        ResultActions resultActions = mockMvc.perform(
                delete("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")

        );
        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        )));
    }

    @Test
    @DisplayName("유저 삭제 실패 / 존재하지 않는 유저")
    void delete_user_fail_not_found() throws Exception {

        // given
        willThrow(new ApplicationException(USER_NOT_FOUND))
                .given(userCommandUseCase).delete(any());
        // when
        ResultActions resultActions = mockMvc.perform(
                delete("/api/v1/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")

        );
        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(USER_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(USER_NOT_FOUND.getMessage())));
    }
}

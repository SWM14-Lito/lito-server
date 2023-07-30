package com.swm.lito.api.auth.adapter.in.presentation;

import com.swm.lito.api.auth.adapter.in.request.LoginRequest;
import com.swm.lito.core.auth.application.port.in.AuthUseCase;
import com.swm.lito.core.auth.application.port.in.response.LoginResponseDto;
import com.swm.lito.core.auth.application.port.in.response.ReissueTokenResponseDto;
import com.swm.lito.api.common.exception.ApplicationException;
import com.swm.lito.core.common.exception.auth.AuthErrorCode;
import com.swm.lito.api.support.restdocs.RestDocsSupport;
import com.swm.lito.api.support.security.WithMockJwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.swm.lito.api.support.restdocs.RestDocsConfig.field;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.any;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockJwt
public class AuthControllerTest extends RestDocsSupport {

    @MockBean
    private AuthUseCase authUseCase;

    private final String ACCESS_TOKEN = "testAccessToken";
    private final String REFRESH_TOKEN = "testRefreshToken";
    private final String NEW_ACCESS_TOKEN = "refreshedTestAccessToken";
    private final String NEW_REFRESH_TOKEN = "refreshedTestRefreshToken";

    @Test
    @DisplayName("로그인 성공")
    void login_success() throws Exception {
        //given
        String provider = "kakao";
        LoginRequest request = LoginRequest.builder()
                .oauthId("kakaoId")
                .email("test@test.com")
                .build();
        LoginResponseDto dto = LoginResponseDto.of(1L, ACCESS_TOKEN, REFRESH_TOKEN, true);
        given(authUseCase.login(any(), any()))
                .willReturn(dto);
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/auth/{provider}/login",provider)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",is(1)))
                .andExpect(jsonPath("$.accessToken",is("testAccessToken")))
                .andExpect(jsonPath("$.refreshToken",is("testRefreshToken")))
                .andExpect(jsonPath("$.registered",is(true)))
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("provider").description("Oauth provider( kakao or apple )")
                        ),
                        requestFields(
                                fieldWithPath("oauthId").type(JsonFieldType.STRING).description("oauth 고유 식별 id"),
                                fieldWithPath("email").optional().type(JsonFieldType.STRING).description("유저 email")
                        )
                        ,
                        responseFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 고유 id"),
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("JWT Access Token"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("JWT Refresh Token"),
                                fieldWithPath("registered").type(JsonFieldType.BOOLEAN).description("등록된 유저 true, 최초 로그인 false")
                        )
                ));

    }

    @Test
    @DisplayName("로그인 실패 / 제공하지 않는 oauth provider")
    void login_fail_unsupported_oauth_provider() throws Exception {

        //given
        String provider = "naver";
        LoginRequest request = LoginRequest.builder()
                .oauthId("naverId")
                .email("test@naver.com")
                .build();
        willThrow(new ApplicationException(AuthErrorCode.INVALID_OAUTH))
                .given(authUseCase).login(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/auth/{provider}/login",provider)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(AuthErrorCode.INVALID_OAUTH.getCode())))
                .andExpect(jsonPath("$.message",is(AuthErrorCode.INVALID_OAUTH.getMessage())));
    }

    @Test
    @DisplayName("로그인 실패 / 입력 조건에 대한 예외")
    void login_fail_not_valid() throws Exception {

        //given
        String provider = "kakao";
        LoginRequest request = LoginRequest.builder()
                .oauthId("")
                .email("test@test.com")
                .build();
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/auth/{provider}/login",provider)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors",hasSize(1)));
    }

    @Test
    @DisplayName("로그아웃 성공")
    void logout_success() throws Exception {

        //given
        String accessToken = "Bearer testAccessToken";
        String refreshToken = REFRESH_TOKEN;
        willDoNothing().given(authUseCase).logout(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .header("REFRESH_TOKEN",refreshToken)
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer")),
                                headerWithName("REFRESH_TOKEN").description("JWT Refresh Token")
                        )
                ));
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void reissue_success() throws Exception {

        //given
        ReissueTokenResponseDto responseDto = ReissueTokenResponseDto.of(NEW_ACCESS_TOKEN, NEW_REFRESH_TOKEN);
        given(authUseCase.reissue(any(),any()))
                .willReturn(responseDto);
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/auth/reissue")
                        .header(HttpHeaders.AUTHORIZATION, REFRESH_TOKEN)
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken",is(NEW_ACCESS_TOKEN)))
                .andExpect(jsonPath("$.refreshToken",is(NEW_REFRESH_TOKEN)))
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Refresh Token").attributes(field("constraints", "JWT Refresh Token With Bearer"))
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("New JWT Access Token"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("유효기간 이상 남아있을 경우 기존 Refresh Token, 그렇지 않을 경우 새로운 Refresh Token")
                        )
                ));
    }

    @Test
    @DisplayName("토큰 재발급 실패 / 존재하지 않는 refresh token")
    void reissue_fail_not_found() throws Exception {

        //given
        willThrow(new ApplicationException(AuthErrorCode.NOT_FOUND_REFRESH_TOKEN))
                .given(authUseCase).reissue(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/auth/reissue")
                        .header(HttpHeaders.AUTHORIZATION, REFRESH_TOKEN)
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(AuthErrorCode.NOT_FOUND_REFRESH_TOKEN.getCode())))
                .andExpect(jsonPath("$.message",is(AuthErrorCode.NOT_FOUND_REFRESH_TOKEN.getMessage())));
    }

}

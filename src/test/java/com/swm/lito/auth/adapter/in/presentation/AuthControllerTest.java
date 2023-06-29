package com.swm.lito.auth.adapter.in.presentation;

import com.swm.lito.auth.application.port.in.response.LoginResponseDto;
import com.swm.lito.auth.application.service.AuthService;
import com.swm.lito.support.restdocs.RestDocsSupport;
import com.swm.lito.support.security.WithMockJwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.any;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;



@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockJwt
public class AuthControllerTest extends RestDocsSupport {

    @MockBean
    private AuthService authService;

    private final String OAUTH_ACCESS_TOKEN = "testOauthAccessToken";
    private final String ACCESS_TOKEN = "testAccessToken";
    private final String REFRESH_TOKEN = "testRefreshToken";

    @Test
    @DisplayName("로그인 성공 / kakao")
    void login_success_kakao() throws Exception {
        //given
        String provider = "kakao";
        LoginResponseDto loginResponseDto = LoginResponseDto.of(1L, ACCESS_TOKEN, REFRESH_TOKEN, true);
        given(authService.login(any(), any()))
                .willReturn(loginResponseDto);
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/auth/{provider}/login",provider)
                .header("OauthAccessToken",OAUTH_ACCESS_TOKEN)
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
                        )
                        ,
                        requestHeaders(
                                headerWithName("OauthAccessToken").description("Oauth access token")
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
}

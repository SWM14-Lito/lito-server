package com.swm.lito.problem.adapter.in.presentation;

import com.swm.lito.problem.application.port.in.ProblemQueryUseCase;
import com.swm.lito.problem.application.port.in.response.ProblemUserResponseDto;
import com.swm.lito.support.restdocs.RestDocsSupport;
import com.swm.lito.support.security.WithMockJwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.swm.lito.support.restdocs.RestDocsConfig.field;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProblemController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockJwt
class ProblemControllerTest extends RestDocsSupport {

    @MockBean
    private ProblemQueryUseCase problemQueryUseCase;

    @Test
    @DisplayName("학습 메인 화면 조회 성공")
    void find_problem_user_success() throws Exception {

        //given
        ProblemUserResponseDto responseDto = ProblemUserResponseDto.builder()
                .userId(1L)
                .profileImgUrl("프로필 이미지")
                .nickname("닉네임")
                .problemId(1L)
                .subject("운영체제")
                .favorite(true)
                .build();
        given(problemQueryUseCase.findProblemUser(any()))
                .willReturn(responseDto);
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/problems/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId",is(1)))
                .andExpect(jsonPath("$.profileImgUrl",is("프로필 이미지")))
                .andExpect(jsonPath("$.nickname",is("닉네임")))
                .andExpect(jsonPath("$.problemId",is(1)))
                .andExpect(jsonPath("$.subject",is("운영체제")))
                .andExpect(jsonPath("$.favorite",is(true)))
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        responseFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 고유 id"),
                                fieldWithPath("profileImgUrl").type(JsonFieldType.STRING).description("유저 프로필 이미지 URL"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                fieldWithPath("problemId").type(JsonFieldType.NUMBER).description("문제 id, 풀던 문제 없을 경우 null"),
                                fieldWithPath("subject").type(JsonFieldType.STRING).description("문제 과목, 풀던 문제 없을 경우 null"),
                                fieldWithPath("favorite").type(JsonFieldType.BOOLEAN).description("찜한 문제 여부, 풀던 문제 없을 경우 false")
                        )
                ));
    }
}
package com.swm.lito.problem.adapter.in.presentation;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.problem.ProblemErrorCode;
import com.swm.lito.common.exception.user.UserErrorCode;
import com.swm.lito.problem.application.port.in.ProblemQueryUseCase;
import com.swm.lito.problem.application.port.in.response.FaqResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemPageResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemResponseDto;
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

import java.util.List;

import static com.swm.lito.support.restdocs.RestDocsConfig.field;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
                .question("질문")
                .favorite(true)
                .build();
        given(problemQueryUseCase.findProblemUser(any()))
                .willReturn(responseDto);
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/problems/users")
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
                .andExpect(jsonPath("$.question",is("질문")))
                .andExpect(jsonPath("$.favorite",is(true)))
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        responseFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 고유 id"),
                                fieldWithPath("profileImgUrl").type(JsonFieldType.STRING).description("유저 프로필 이미지 URL, 프로필 이미지 등록 하지 않았을 경우 null"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                fieldWithPath("problemId").type(JsonFieldType.NUMBER).description("문제 id, 풀던 문제 없을 경우 null"),
                                fieldWithPath("subject").type(JsonFieldType.STRING).description("문제 과목, 풀던 문제 없을 경우 null"),
                                fieldWithPath("question").type(JsonFieldType.STRING).description("문제 질문, 풀던 문제 없을 경우 null"),
                                fieldWithPath("favorite").type(JsonFieldType.BOOLEAN).description("찜한 문제 여부, 풀던 문제 없을 경우 false")
                        )
                ));
    }

    @Test
    @DisplayName("학습 메인 화면 조회 실패 / 존재하지 않는 유저")
    void find_problem_user_fail_user_not_found() throws Exception {

        //given
        willThrow(new ApplicationException(UserErrorCode.USER_NOT_FOUND))
                .given(problemQueryUseCase).findProblemUser(any());
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/problems/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(UserErrorCode.USER_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(UserErrorCode.USER_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName("학습 메인 화면 조회 실패 / 존재하지 않는 문제")
    void find_problem_user_fail_problem_not_found() throws Exception {

        //given
        willThrow(new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND))
                .given(problemQueryUseCase).findProblemUser(any());
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/problems/users")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(ProblemErrorCode.PROBLEM_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(ProblemErrorCode.PROBLEM_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName("문제 질문 목록 조회 성공")
    void find_problem_page_success() throws Exception {

        //given
        List<ProblemPageResponseDto> responseDtos = findProblemPage();
        given(problemQueryUseCase.findProblemPage(any(),any(),any(),any(),any(),any()))
                .willReturn(responseDtos);
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/problems")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .queryParam("size","10")
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.problems[0].problemId",is(1)))
                .andExpect(jsonPath("$.problems[0].subjectName",is("운영체제")))
                .andExpect(jsonPath("$.problems[0].question",is("문제 질문1")))
                .andExpect(jsonPath("$.problems[0].problemStatus",is("풀이중")))
                .andExpect(jsonPath("$.problems[0].favorite",is(true)))
                .andExpect(jsonPath("$.problems[1].problemId",is(2)))
                .andExpect(jsonPath("$.problems[1].subjectName",is("운영체제")))
                .andExpect(jsonPath("$.problems[1].question",is("문제 질문2")))
                .andExpect(jsonPath("$.problems[1].problemStatus",is("풀이완료")))
                .andExpect(jsonPath("$.problems[1].favorite",is(false)))

                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        queryParameters(
                                parameterWithName("lastProblemId").optional().description("마지막으로 조회된 problemId값, 첫 조회시 필요없음"),
                                parameterWithName("subjectId").optional().description("과목 번호, 전체 조회시 필요없음. 운영체제 ->1, 네트워크 ->2, 데이터베이스 ->3," +
                                        "자료구조 ->4"),
                                parameterWithName("problemStatus").optional().description("문제 상태값(풀이완료 -> COMPLETE, 풀지않음 -> PROCESS), 입력 안할 시 전체"),
                                parameterWithName("query").optional().description("제목 검색 키워드"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("problems[].problemId").type(JsonFieldType.NUMBER).description("문제 id"),
                                fieldWithPath("problems[].subjectName").type(JsonFieldType.STRING).description("과목명"),
                                fieldWithPath("problems[].question").type(JsonFieldType.STRING).description("문제 질문"),
                                fieldWithPath("problems[].problemStatus").type(JsonFieldType.STRING).description("문제 상태"),
                                fieldWithPath("problems[].favorite").type(JsonFieldType.BOOLEAN).description("찜 여부")
                        )
                ));

    }

    private static List<ProblemPageResponseDto> findProblemPage(){
        return List.of(ProblemPageResponseDto.builder()
                .problemId(1L)
                .subjectName("운영체제")
                .question("문제 질문1")
                .problemStatus("풀이중")
                .favorite(true)
                .build(),
                ProblemPageResponseDto.builder()
                        .problemId(2L)
                        .subjectName("운영체제")
                        .question("문제 질문2")
                        .problemStatus("풀이완료")
                        .favorite(false)
                        .build());
    }

    @Test
    @DisplayName("문제 세부 조회 성공")
    void find_problem_success() throws Exception {

        //given
        ProblemResponseDto response = findProblem();
        given(problemQueryUseCase.find(any(), any()))
                .willReturn(response);
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/problems/{id}",1L)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAccessToken")
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.problemId",is(1)))
                .andExpect(jsonPath("$.problemQuestion",is("문제 질문")))
                .andExpect(jsonPath("$.problemAnswer",is("문제 답변")))
                .andExpect(jsonPath("$.problemKeyword",is("키워드")))
                .andExpect(jsonPath("$.faqs[0].faqQuestion",is("faq 질문")))
                .andExpect(jsonPath("$.faqs[0].faqAnswer",is("faq 답변")))

                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        pathParameters(
                                parameterWithName("id").description("문제 id")
                        ),
                        responseFields(
                                fieldWithPath("problemId").type(JsonFieldType.NUMBER).description("문제 id"),
                                fieldWithPath("problemQuestion").type(JsonFieldType.STRING).description("문제 질문"),
                                fieldWithPath("problemAnswer").type(JsonFieldType.STRING).description("문제 답변"),
                                fieldWithPath("problemKeyword").type(JsonFieldType.STRING).description("문제 키워드"),
                                fieldWithPath("faqs[].faqQuestion").type(JsonFieldType.STRING).description("faq 질문"),
                                fieldWithPath("faqs[].faqAnswer").type(JsonFieldType.STRING).description("faq 답변")
                        )
                ));
    }

    private static ProblemResponseDto findProblem(){
        return ProblemResponseDto.builder()
                .problemId(1L)
                .problemQuestion("문제 질문")
                .problemAnswer("문제 답변")
                .problemKeyword("키워드")
                .faqResponseDtos(List.of(FaqResponseDto.builder()
                        .faqQuestion("faq 질문")
                        .faqAnswer("faq 답변")
                        .build()))
                .build();
    }

    @Test
    @DisplayName("문제 세부 조회 실패 / 존재하지 않는 문제")
    void find_problem_fail_not_found() throws Exception {

        //given
        given(problemQueryUseCase.find(any(), any()))
                .willThrow(new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/problems/{id}",1L)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAccessToken")
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(ProblemErrorCode.PROBLEM_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(ProblemErrorCode.PROBLEM_NOT_FOUND.getMessage())));

    }

}
package com.lito.api.problem.adapter.in.web;

import com.lito.api.support.restdocs.RestDocsSupport;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.problem.ProblemErrorCode;
import com.lito.core.common.exception.user.UserErrorCode;
import com.lito.core.problem.application.port.in.response.*;
import com.lito.api.problem.adapter.in.request.ProblemSubmitRequest;
import com.lito.core.problem.application.port.in.ProblemCommandUseCase;
import com.lito.core.problem.application.port.in.ProblemQueryUseCase;
import com.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.lito.core.problem.domain.enums.ProblemStatus;
import com.lito.api.support.security.WithMockJwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.lito.api.support.restdocs.RestDocsConfig.field;
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

@WebMvcTest(ProblemController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockJwt
class ProblemControllerTest extends RestDocsSupport {

    @MockBean
    private ProblemCommandUseCase problemCommandUseCase;

    @MockBean
    private ProblemQueryUseCase problemQueryUseCase;

    @Test
    @DisplayName("홈 화면 문제 조회 성공")
    void find_home_success() throws Exception {

        //given
        ProcessProblemResponseDto processProblemResponseDto = ProcessProblemResponseDto.builder()
                .problemId(1L)
                .subjectName("운영체제")
                .question("질문")
                .favorite(true)
                .build();

        RecommendUserResponseDto recommendUserResponseDto = RecommendUserResponseDto.builder()
                .problemId(2L)
                .subjectName("네트워크")
                .question("네트워크 질문")
                .problemStatus(ProblemStatus.NOT_SEEN.getName())
                .favorite(false)
                .build();

        ProblemHomeResponseDto responseDto = ProblemHomeResponseDto.builder()
                .userId(1L)
                .profileImgUrl("프로필 이미지")
                .nickname("닉네임")
                .processProblemResponseDto(processProblemResponseDto)
                .recommendUserResponseDtos(List.of(recommendUserResponseDto))
                .build();

        given(problemQueryUseCase.findHome(any()))
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
                .andExpect(jsonPath("$.processProblem.problemId",is(1)))
                .andExpect(jsonPath("$.processProblem.subjectName",is("운영체제")))
                .andExpect(jsonPath("$.processProblem.question",is("질문")))
                .andExpect(jsonPath("$.processProblem.problemStatus",is("풀이중")))
                .andExpect(jsonPath("$.processProblem.favorite",is(true)))
                .andExpect(jsonPath("$.recommendProblems[0].problemId",is(2)))
                .andExpect(jsonPath("$.recommendProblems[0].subjectName",is("네트워크")))
                .andExpect(jsonPath("$.recommendProblems[0].question",is("네트워크 질문")))
                .andExpect(jsonPath("$.recommendProblems[0].problemStatus",is("풀지않음")))
                .andExpect(jsonPath("$.recommendProblems[0].favorite",is(false)))

                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        responseFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 고유 id"),
                                fieldWithPath("profileImgUrl").type(JsonFieldType.STRING).description("유저 프로필 이미지 URL, 프로필 이미지 등록 하지 않았을 경우 null"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                fieldWithPath("processProblem").type(JsonFieldType.OBJECT).description("풀던 문제 객체"),
                                fieldWithPath("processProblem.problemId").type(JsonFieldType.NUMBER).description("문제 id, 풀던 문제 없을 경우 null"),
                                fieldWithPath("processProblem.subjectName").type(JsonFieldType.STRING).description("문제 과목, 풀던 문제 없을 경우 null"),
                                fieldWithPath("processProblem.question").type(JsonFieldType.STRING).description("문제 질문, 풀던 문제 없을 경우 null"),
                                fieldWithPath("processProblem.problemStatus").type(JsonFieldType.STRING).description("풀이중 명시, 풀던 문제 없을 경우 null"),
                                fieldWithPath("processProblem.favorite").type(JsonFieldType.BOOLEAN).description("찜한 문제 여부, 풀던 문제 없을 경우 false"),
                                fieldWithPath("recommendProblems[]").type(JsonFieldType.ARRAY).description("추천 문제 리스트"),
                                fieldWithPath("recommendProblems[].problemId").type(JsonFieldType.NUMBER).description("추천 문제 id"),
                                fieldWithPath("recommendProblems[].subjectName").type(JsonFieldType.STRING).description("추천 문제 과목"),
                                fieldWithPath("recommendProblems[].question").type(JsonFieldType.STRING).description("추천 문제 질문"),
                                fieldWithPath("recommendProblems[].problemStatus").type(JsonFieldType.STRING).description("추천 문제 풀이 상태"),
                                fieldWithPath("recommendProblems[].favorite").type(JsonFieldType.BOOLEAN).description("추천 문제 찜한 여부")
                        )
                ));
    }

    @Test
    @DisplayName("홈 화면 문제 조회 실패 / 존재하지 않는 유저")
    void find_home_fail_user_not_found() throws Exception {

        //given
        willThrow(new ApplicationException(UserErrorCode.USER_NOT_FOUND))
                .given(problemQueryUseCase).findHome(any());
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
    @DisplayName("홈 화면 문제 조회 실패 / 존재하지 않는 문제")
    void find_home_fail_problem_not_found() throws Exception {

        //given
        willThrow(new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND))
                .given(problemQueryUseCase).findHome(any());
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
        Page<ProblemPageQueryDslResponseDto> responseDtos = findProblemPage();
        given(problemQueryUseCase.findProblemPage(any(),any(),any(),any(),any()))
                .willReturn(responseDtos);
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/problems")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .param("page","0")
                        .param("size","10")
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.problems[0].problemId",is(2)))
                .andExpect(jsonPath("$.problems[0].subjectName",is("운영체제")))
                .andExpect(jsonPath("$.problems[0].question",is("문제 질문2")))
                .andExpect(jsonPath("$.problems[0].problemStatus",is("풀이완료")))
                .andExpect(jsonPath("$.problems[0].favorite",is(false)))
                .andExpect(jsonPath("$.problems[1].problemId",is(1)))
                .andExpect(jsonPath("$.problems[1].subjectName",is("운영체제")))
                .andExpect(jsonPath("$.problems[1].question",is("문제 질문1")))
                .andExpect(jsonPath("$.problems[1].problemStatus",is("풀이중")))
                .andExpect(jsonPath("$.problems[1].favorite",is(true)))
                .andExpect(jsonPath("$.total",is(2)))

                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        queryParameters(
                                parameterWithName("subjectId").optional().description("과목 번호, 전체 조회시 필요없음. 운영체제 ->1, 네트워크 ->2, 데이터베이스 ->3," +
                                        "자료구조 ->4"),
                                parameterWithName("problemStatus").optional().description("문제 상태값(풀이완료 -> COMPLETE, 풀지않음 -> PROCESS), 입력 안할 시 전체"),
                                parameterWithName("query").optional().description("제목 검색 키워드"),
                                parameterWithName("page").description("페이지 번호 (0부터 시작)"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("problems[].problemId").type(JsonFieldType.NUMBER).description("문제 id"),
                                fieldWithPath("problems[].subjectName").type(JsonFieldType.STRING).description("과목명"),
                                fieldWithPath("problems[].question").type(JsonFieldType.STRING).description("문제 질문"),
                                fieldWithPath("problems[].problemStatus").type(JsonFieldType.STRING).description("문제 상태"),
                                fieldWithPath("problems[].favorite").type(JsonFieldType.BOOLEAN).description("찜 여부"),
                                fieldWithPath("total").type(JsonFieldType.NUMBER).description("조회된 전체 개수")
                        )
                ));

    }

    private static Page<ProblemPageQueryDslResponseDto> findProblemPage(){
        return new PageImpl<>(List.of(ProblemPageQueryDslResponseDto.builder()
                        .problemId(2L)
                        .subjectName("운영체제")
                        .question("문제 질문2")
                        .problemStatus(ProblemStatus.COMPLETE)
                        .favorite(false)
                        .build(),
                        ProblemPageQueryDslResponseDto.builder()
                                .problemId(1L)
                                .subjectName("운영체제")
                                .question("문제 질문1")
                                .problemStatus(ProblemStatus.PROCESS)
                                .favorite(true)
                                .build()));
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
                .andExpect(jsonPath("$.problemStatus",is("풀이중")))
                .andExpect(jsonPath("$.favorite",is(true)))
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
                                fieldWithPath("problemStatus").type(JsonFieldType.STRING).description("문제 풀이 상태"),
                                fieldWithPath("favorite").type(JsonFieldType.BOOLEAN).description("문제 찜 여부"),
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
                .problemStatus(ProblemStatus.PROCESS.getName())
                .favorite(true)
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

    @Test
    @DisplayName("문제 풀이 진행 성공")
    void create_problem_user_success() throws Exception {

        // given
        willDoNothing().given(problemCommandUseCase).createProblemUser(any(),any());
        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/problems/{id}",1L)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAccessToken")
        );
        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        pathParameters(
                                parameterWithName("id").description("문제 id")
                        )
                ));
    }

    @Test
    @DisplayName("문제 풀이 진행 실패 / 존재하지 않는 문제")
    void create_problem_user_fail_not_found_problem() throws Exception {

        // given
        willThrow(new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND))
                .given(problemCommandUseCase).createProblemUser(any(),any());
        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/problems/{id}",1L)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAccessToken")
        );
        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(ProblemErrorCode.PROBLEM_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(ProblemErrorCode.PROBLEM_NOT_FOUND.getMessage())));
    }


    @Test
    @DisplayName("풀던 문제 질문 목록 조회 성공")
    void find_problem_process_status_success() throws Exception {

        //given
        Page<ProblemPageWithProcessQResponseDto> responseDtos = findProblemPageWithProcess();
        given(problemQueryUseCase.findProblemPageWithProcess(any(),any()))
                .willReturn(responseDtos);
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/problems/process-status")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                            .param("page","0")
                            .param("size","10")
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.problems[0].problemUserId",is(2)))
                .andExpect(jsonPath("$.problems[0].problemId",is(2)))
                .andExpect(jsonPath("$.problems[0].subjectName",is("운영체제")))
                .andExpect(jsonPath("$.problems[0].question",is("문제 질문2")))
                .andExpect(jsonPath("$.problems[0].favorite",is(false)))
                .andExpect(jsonPath("$.problems[1].problemUserId",is(1)))
                .andExpect(jsonPath("$.problems[1].problemId",is(1)))
                .andExpect(jsonPath("$.problems[1].subjectName",is("운영체제")))
                .andExpect(jsonPath("$.problems[1].question",is("문제 질문1")))
                .andExpect(jsonPath("$.problems[1].favorite",is(true)))
                .andExpect(jsonPath("$.total",is(2)))

                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        queryParameters(
                                parameterWithName("lastProblemUserId").optional().description("마지막으로 조회된 problemUserId값, 첫 조회시 필요없음"),
                                parameterWithName("page").description("페이지 번호 (0부터 시작)"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("problems[].problemUserId").type(JsonFieldType.NUMBER).description("문제와 유저 관계 id"),
                                fieldWithPath("problems[].problemId").type(JsonFieldType.NUMBER).description("문제 id"),
                                fieldWithPath("problems[].subjectName").type(JsonFieldType.STRING).description("과목명"),
                                fieldWithPath("problems[].question").type(JsonFieldType.STRING).description("문제 질문"),
                                fieldWithPath("problems[].favorite").type(JsonFieldType.BOOLEAN).description("찜 여부"),
                                fieldWithPath("total").type(JsonFieldType.NUMBER).description("조회된 전체 개수")
                        )
                ));
    }

    private Page<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(){
        return new PageImpl<>(List.of(ProblemPageWithProcessQResponseDto.builder()
                        .problemUserId(2L)
                        .problemId(2L)
                        .subjectName("운영체제")
                        .question("문제 질문2")
                        .favorite(false)
                        .build(),
                        ProblemPageWithProcessQResponseDto.builder()
                                .problemUserId(1L)
                                .problemId(1L)
                                .subjectName("운영체제")
                                .question("문제 질문1")
                                .favorite(true)
                                .build()));
    }

    @Test
    @DisplayName("문제 찜하기 성공")
    void update_favorite_success() throws Exception {

        //given
        willDoNothing().given(problemCommandUseCase).updateFavorite(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/problems/{id}/favorites",1L)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")

        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        pathParameters(
                                parameterWithName("id").description("문제 id")
                        )
                ));
    }

    @Test
    @DisplayName("문제 찜하기 실패 / 존재하지 않는 문제")
    void update_favorite_fail_problem_not_found() throws Exception {

        //given
        willThrow(new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND))
                .given(problemCommandUseCase).updateFavorite(any(),any());
        //when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/problems/{id}/favorites",1L)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")

        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(ProblemErrorCode.PROBLEM_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(ProblemErrorCode.PROBLEM_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName("찜한 문제 목록 조회 성공")
    void find_problem_favorite_success() throws Exception {

        //given
        Page<ProblemPageWithFavoriteQResponseDto> responseDtos = findProblemPageWithFavorite();
        given(problemQueryUseCase.findProblemPageWithFavorite(any(),any(),any(),any()))
                .willReturn(responseDtos);
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/problems/favorites")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                            .param("page","0")
                            .param("size","10")
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.problems[0].favoriteId",is(2)))
                .andExpect(jsonPath("$.problems[0].problemId",is(2)))
                .andExpect(jsonPath("$.problems[0].subjectName",is("운영체제")))
                .andExpect(jsonPath("$.problems[0].question",is("문제 질문2")))
                .andExpect(jsonPath("$.problems[0].problemStatus",is("풀이완료")))
                .andExpect(jsonPath("$.problems[1].favoriteId",is(1)))
                .andExpect(jsonPath("$.problems[1].problemId",is(1)))
                .andExpect(jsonPath("$.problems[1].subjectName",is("운영체제")))
                .andExpect(jsonPath("$.problems[1].question",is("문제 질문1")))
                .andExpect(jsonPath("$.problems[1].problemStatus",is("풀이중")))
                .andExpect(jsonPath("$.total",is(2)))

                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        queryParameters(
                                parameterWithName("lastFavoriteId").optional().description("마지막으로 조회된 favoriteId값, 첫 조회시 필요없음"),
                                parameterWithName("subjectId").optional().description("과목 번호, 전체 조회시 필요없음. 운영체제 ->1, 네트워크 ->2, 데이터베이스 ->3," +
                                        "자료구조 ->4"),
                                parameterWithName("problemStatus").optional().description("문제 상태값(풀이완료 -> COMPLETE, 풀지않음 -> PROCESS), 입력 안할 시 전체"),
                                parameterWithName("page").description("페이지 번호 (0부터 시작)"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("problems[].favoriteId").type(JsonFieldType.NUMBER).description("찜한 문제 id"),
                                fieldWithPath("problems[].problemId").type(JsonFieldType.NUMBER).description("문제 id"),
                                fieldWithPath("problems[].subjectName").type(JsonFieldType.STRING).description("과목명"),
                                fieldWithPath("problems[].question").type(JsonFieldType.STRING).description("문제 질문"),
                                fieldWithPath("problems[].problemStatus").type(JsonFieldType.STRING).description("문제 상태"),
                                fieldWithPath("total").type(JsonFieldType.NUMBER).description("조회된 전체 개수")
                        )
                ));
    }

    private Page<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(){
        return new PageImpl<>(List.of(ProblemPageWithFavoriteQResponseDto.builder()
                .favoriteId(2L)
                .problemId(2L)
                .subjectName("운영체제")
                .question("문제 질문2")
                .problemStatus(ProblemStatus.COMPLETE)
                .build(),
                ProblemPageWithFavoriteQResponseDto.builder()
                        .favoriteId(1L)
                        .problemId(1L)
                        .subjectName("운영체제")
                        .question("문제 질문1")
                        .problemStatus(ProblemStatus.PROCESS)
                        .build()));
    }

    @Test
    @DisplayName("문제 제출 성공")
    void submit_problem_success() throws Exception {

        // given
        ProblemSubmitRequest request = ProblemSubmitRequest.builder()
                .keyword("키워드")
                .build();
        ProblemSubmitResponseDto responseDto= ProblemSubmitResponseDto.builder()
                .solved(true)
                .build();
        given(problemCommandUseCase.submit(any(),any(),any()))
                .willReturn(responseDto);
        // when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/problems/{id}/users", 1L)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.solved",is(true)))
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        pathParameters(
                                parameterWithName("id").description("문제 id")
                        ),
                        requestFields(
                                fieldWithPath("keyword").description("유저가 제출한 키워드")
                        ),
                        responseFields(
                                fieldWithPath("solved").description("정답 여부")
                        )
                ));
    }

    @Test
    @DisplayName("문제 제출 실패 / 입력 조건에 대한 예외")
    void submit_problem_fail_not_blank() throws Exception {

        // given
        ProblemSubmitRequest request = ProblemSubmitRequest.builder()
                .keyword("")
                .build();
        ProblemSubmitResponseDto responseDto= ProblemSubmitResponseDto.builder()
                .solved(true)
                .build();
        given(problemCommandUseCase.submit(any(),any(),any()))
                .willReturn(responseDto);
        // when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/problems/{id}/users", 1L)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors",hasSize(1)));
    }

    @Test
    @DisplayName("문제 제출 실패 / 존재하지 않는 문제")
    void submit_problem_fail_not_found_problem() throws Exception {

        // given
        ProblemSubmitRequest request = ProblemSubmitRequest.builder()
                .keyword("키워드")
                .build();
        willThrow(new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND))
                .given(problemCommandUseCase).submit(any(),any(),any());
        // when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/problems/{id}/users", 1L)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(ProblemErrorCode.PROBLEM_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(ProblemErrorCode.PROBLEM_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName("문제 제출 실패 / 올바르지 않은 문제 접근")
    void submit_problem_fail_invalid() throws Exception {

        // given
        ProblemSubmitRequest request = ProblemSubmitRequest.builder()
                .keyword("키워드")
                .build();
        willThrow(new ApplicationException(ProblemErrorCode.PROBLEM_INVALID))
                .given(problemCommandUseCase).submit(any(),any(),any());
        // when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/problems/{id}/users", 1L)
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(ProblemErrorCode.PROBLEM_INVALID.getCode())))
                .andExpect(jsonPath("$.message",is(ProblemErrorCode.PROBLEM_INVALID.getMessage())));
    }
}
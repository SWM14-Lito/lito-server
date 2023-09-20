package com.lito.admin.problem.adapter.in.web;

import com.lito.admin.config.SecurityConfig;
import com.lito.admin.problem.adapter.in.request.FaqRequest;
import com.lito.admin.problem.adapter.in.request.ProblemRequest;
import com.lito.admin.support.restdocs.RestDocsSupport;
import com.lito.core.admin.application.port.in.AdminProblemCommandUseCase;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.admin.AdminErrorCode;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminProblemController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockUser(username = "admin", roles = "ADMIN")
@Import(SecurityConfig.class)
class AdminProblemControllerTest extends RestDocsSupport {

    @MockBean
    private AdminProblemCommandUseCase adminProblemCommandUseCase;

    private final String COOKIE = UUID.randomUUID().toString();

    @Test
    @DisplayName("문제 생성 성공")
    void create_problem_success() throws Exception{

        //given
        ProblemRequest request = ProblemRequest.builder()
                .subjectId(1L)
                .subjectCategoryId(1L)
                .question("질문")
                .answer("답변")
                .keyword("키워드")
                .faqs(List.of(
                        FaqRequest.builder()
                                .question("FAQ 질문")
                                .answer("FAQ 답변")
                                .build()
                ))
                .build();
        willDoNothing().given(adminProblemCommandUseCase).create(any());
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/admin/problems")
                        .cookie(new Cookie("JSESSIONID", COOKIE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("subjectId").type(JsonFieldType.NUMBER).description("과목 id"),
                                fieldWithPath("subjectCategoryId").type(JsonFieldType.NUMBER).description("과목 목차 id"),
                                fieldWithPath("question").type(JsonFieldType.STRING).description("문제 질문"),
                                fieldWithPath("answer").type(JsonFieldType.STRING).description("문제 답변"),
                                fieldWithPath("keyword").type(JsonFieldType.STRING).description("문제 키워드"),
                                fieldWithPath("faqs[].question").type(JsonFieldType.STRING).optional().description("FAQ 질문"),
                                fieldWithPath("faqs[].answer").type(JsonFieldType.STRING).optional().description("FAQ 답변")
                        )
                ));
    }

    @Test
    @DisplayName("문제 생성 실패 / 입력 조건에 대한 예외")
    void create_problem_fail_invalid() throws Exception{

        //given
        ProblemRequest request = ProblemRequest.builder()
                .subjectId(0L)
                .subjectCategoryId(0L)
                .build();
        willDoNothing().given(adminProblemCommandUseCase).create(any());
        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/admin/problems")
                        .cookie(new Cookie("JSESSIONID", COOKIE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors",hasSize(5)));

    }

    @Test
    @DisplayName("문제 삭제 성공")
    void delete_problem_success() throws Exception{

        //given
        Long problemId = 1L;
        willDoNothing().given(adminProblemCommandUseCase).delete(problemId);
        //when
        ResultActions resultActions = mockMvc.perform(
                delete("/api/v1/admin/problems/{id}",problemId)
                        .cookie(new Cookie("JSESSIONID", COOKIE))
        );
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("id").description("문제 id")
                        )
                ));
    }

    @Test
    @DisplayName("문제 삭제 실패 / 존재하지 않는 문제")
    void delete_problem_fail_not_found() throws Exception{

        //given
        Long problemId = 1L;
        willThrow(new ApplicationException(AdminErrorCode.PROBLEM_NOT_FOUND))
                .given(adminProblemCommandUseCase).delete(problemId);
        //when
        ResultActions resultActions = mockMvc.perform(
                delete("/api/v1/admin/problems/{id}",problemId)
                        .cookie(new Cookie("JSESSIONID", COOKIE))
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code",is(AdminErrorCode.PROBLEM_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.message",is(AdminErrorCode.PROBLEM_NOT_FOUND.getMessage())));
    }
}
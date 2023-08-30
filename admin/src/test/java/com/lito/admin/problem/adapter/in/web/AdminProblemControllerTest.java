package com.lito.admin.problem.adapter.in.web;

import com.lito.admin.problem.adapter.in.request.FaqRequest;
import com.lito.admin.problem.adapter.in.request.ProblemRequest;
import com.lito.admin.support.restdocs.RestDocsSupport;
import com.lito.core.admin.application.port.in.AdminProblemCommandUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminProblemController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockUser(username = "admin", roles = "ADMIN")
class AdminProblemControllerTest extends RestDocsSupport {

    @MockBean
    private AdminProblemCommandUseCase adminProblemCommandUseCase;

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
                        .with(csrf())
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
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors",hasSize(5)));

    }
}
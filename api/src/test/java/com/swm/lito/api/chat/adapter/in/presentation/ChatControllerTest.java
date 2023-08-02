package com.swm.lito.api.chat.adapter.in.presentation;


import com.swm.lito.api.chat.adapter.in.request.ChatGPTCompletionRequest;
import com.swm.lito.api.support.restdocs.RestDocsSupport;
import com.swm.lito.api.support.security.WithMockJwt;
import com.swm.lito.core.chat.application.port.in.ChatCommandUseCase;
import com.swm.lito.core.chat.application.port.in.response.ChatGPTCompletionResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.swm.lito.api.support.restdocs.RestDocsConfig.field;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockJwt
class ChatControllerTest extends RestDocsSupport {

    @MockBean
    private ChatCommandUseCase chatCommandUseCase;

    @Test
    @DisplayName("chat-gpt 질문하기 성공")
    void send_success() throws Exception {

        // given
        ChatGPTCompletionRequest request = createCompletionRequest();
        ChatGPTCompletionResponseDto responseDto = createCompletionResponseDto();
        given(chatCommandUseCase.send(any()))
                .willReturn(responseDto);
        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/chat-gpt")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer testAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is("chatcmpl-7j05y1QjDWTsAqsRipw7tfJ4nPqsB")))
                .andExpect(jsonPath("$.object",is("chat.completion")))
                .andExpect(jsonPath("$.created",is(1690959482)))
                .andExpect(jsonPath("$.model",is("gpt-3.5-turbo-0613")))
                .andExpect(jsonPath("$.messages[0].role",is("assistant")))
                .andExpect(jsonPath("$.messages[0].message",is("응답 메세지")))
                .andExpect(jsonPath("$.usage.promptTokens",is(13)))
                .andExpect(jsonPath("$.usage.completionTokens",is(44)))
                .andExpect(jsonPath("$.usage.totalTokens",is(57)))

                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT Access Token").attributes(field("constraints", "JWT Access Token With Bearer"))
                        ),
                        requestFields(
                                fieldWithPath("model").type(JsonFieldType.STRING).description("gpt-3.5-turbo 고정"),
                                fieldWithPath("role").type(JsonFieldType.STRING).description("user 고정"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("질문 메세지"),
                                fieldWithPath("maxTokens").type(JsonFieldType.NUMBER).description("prompt와 completion에 사용되는 토큰의 합 최대치")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("chat unique id"),
                                fieldWithPath("object").type(JsonFieldType.STRING).description("chat.completion"),
                                fieldWithPath("created").type(JsonFieldType.NUMBER).description("생성된 유닉스 시간"),
                                fieldWithPath("model").type(JsonFieldType.STRING).description("사용한 chat-gpt 모델"),
                                fieldWithPath("messages[].role").type(JsonFieldType.STRING).description("assistant값 고정(대답을 해주는 챗봇 이라는 의미)"),
                                fieldWithPath("messages[].message").type(JsonFieldType.STRING).description("chat-gpt 응답 메세지"),
                                fieldWithPath("usage.promptTokens").type(JsonFieldType.NUMBER).description("prompt token 사용량"),
                                fieldWithPath("usage.completionTokens").type(JsonFieldType.NUMBER).description("completion token 사용량(메세지)"),
                                fieldWithPath("usage.totalTokens").type(JsonFieldType.NUMBER).description("prompt tokens + completion tokens")
                        )
                ));
    }

    private static ChatGPTCompletionRequest createCompletionRequest(){
        return ChatGPTCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .role("user")
                .message("질문 메세지")
                .maxTokens(1000)
                .build();


    }

    private static ChatGPTCompletionResponseDto createCompletionResponseDto(){
        return ChatGPTCompletionResponseDto.builder()
                .id("chatcmpl-7j05y1QjDWTsAqsRipw7tfJ4nPqsB")
                .object("chat.completion")
                .created(1690959482L)
                .model("gpt-3.5-turbo-0613")
                .messages(createMessages())
                .usage(createResponseDtoUsage())
                .build();
    }

    private static List<ChatGPTCompletionResponseDto.Message> createMessages(){
        return List.of(createMessage());
    }

    private static ChatGPTCompletionResponseDto.Message createMessage(){
        return ChatGPTCompletionResponseDto.Message.builder()
                .role("assistant")
                .message("응답 메세지")
                .build();

    }

    private static ChatGPTCompletionResponseDto.Usage createResponseDtoUsage(){
        return ChatGPTCompletionResponseDto.Usage.builder()
                .promptTokens(13L)
                .completionTokens(44L)
                .totalTokens(57L)
                .build();
    }

}
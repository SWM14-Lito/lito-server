package com.lito.core.chat.application.service;

import com.lito.core.chat.application.port.ChatCommandPort;
import com.lito.core.chat.application.port.in.request.ChatGPTCompletionRequestDto;
import com.lito.core.chat.application.port.in.response.ChatGPTCompletionResponseDto;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.security.AuthUser;
import com.lito.core.problem.adapter.out.persistence.ProblemRepository;
import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.Subject;
import com.lito.core.problem.domain.SubjectCategory;
import com.lito.core.user.adapter.out.persistence.UserRepository;
import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
import com.theokanning.openai.Usage;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lito.core.common.exception.problem.ProblemErrorCode.PROBLEM_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("ChatCommandService")
class ChatCommandServiceTest {

    @Autowired
    ChatCommandService chatCommandService;

    @MockBean
    ChatCommandPort chatCommandPort;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProblemRepository problemRepository;

    User user = User.builder()
            .oauthId("kakaoId")
            .email("test@test.com")
            .provider(Provider.KAKAO)
            .build();
    AuthUser authUser = AuthUser.of(user);

    Problem problem = Problem.builder()
            .subject(Subject.builder()
                    .subjectName("운영체제")
                    .build())
            .subjectCategory(SubjectCategory.builder()
                    .subjectCategoryName("프로세스관리")
                    .build())
            .question("문맥 전환이 무엇인가?")
            .answer("CPU가 이전 상태의 프로세스를 PCB에 보관하고, 또 다른 프로세스를 PCB에서 읽어 레지스터에 적재하는 과정")
            .keyword("PCB")
            .build();

    Long problemId;

    @BeforeEach
    void setUp(){
        userRepository.save(user);
        problemId = problemRepository.save(problem).getId();
    }

    @Nested
    @DisplayName("send 메서드는")
    class send{

        ChatGPTCompletionRequestDto requestDto = ChatGPTCompletionRequestDto.builder()
                .model("model")
                .role("role")
                .message("질문 메세지")
                .build();
        @Nested
        @DisplayName("authUser, problemId, requestDto를 가지고")
        class with_auth_user_problem_id_request_dto{

            @Test
            @DisplayName("ChatGPTCompletionResponseDto를 리턴한다.")
            void it_returns_chat_gpt_completion_response_dto() throws Exception{

                given(chatCommandPort.createChatCompletion(any()))
                        .willReturn(createCompletionResult());

                ChatGPTCompletionResponseDto responseDto = chatCommandService.send(authUser, problemId, requestDto);

                assertThat(responseDto)
                        .extracting("id","Object","created","model")
                        .contains("chatcmpl-7j05y1QjDWTsAqsRipw7tfJ4nPqsB","chat.completion",1690959482L,"gpt-3.5-turbo-0613");

            }

        }

        @Nested
        @DisplayName("만약 존재하지 않는 문제라면")
        class with_problem_not_found{

            Long problemId = 999L;

            @Test
            @DisplayName("PROBLEM_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_problem_not_found() throws Exception{

                assertThatThrownBy(() -> chatCommandService.send(authUser, problemId, requestDto))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(PROBLEM_NOT_FOUND.getMessage());
            }
        }
    }

    private static ChatCompletionResult createCompletionResult(){
        ChatCompletionResult result = new ChatCompletionResult();
        result.setId("chatcmpl-7j05y1QjDWTsAqsRipw7tfJ4nPqsB");
        result.setObject("chat.completion");
        result.setCreated(1690959482L);
        result.setModel("gpt-3.5-turbo-0613");
        result.setChoices(createChatCompletionChoice());
        result.setUsage(createUsage());

        return result;
    }

    private static List<ChatCompletionChoice> createChatCompletionChoice(){
        ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
        chatCompletionChoice.setMessage(new ChatMessage("assistant", "응답 메세지"));

        return List.of(chatCompletionChoice);
    }

    private static Usage createUsage(){
        Usage usage = new Usage();
        usage.setPromptTokens(11L);
        usage.setCompletionTokens(47L);
        usage.setTotalTokens(57L);

        return usage;
    }

}
package com.swm.lito.core.chat.application.service;

import com.swm.lito.core.chat.application.port.ChatCommandPort;
import com.swm.lito.core.chat.application.port.in.ChatCommandUseCase;
import com.swm.lito.core.chat.application.port.in.request.ChatGPTCompletionRequestDto;
import com.swm.lito.core.chat.application.port.in.response.ChatGPTCompletionResponseDto;
import com.swm.lito.core.chat.domain.Chat;
import com.swm.lito.core.common.exception.ApplicationException;
import com.swm.lito.core.common.exception.problem.ProblemErrorCode;
import com.swm.lito.core.common.security.AuthUser;
import com.swm.lito.core.problem.application.port.out.ProblemQueryPort;
import com.swm.lito.core.problem.domain.Problem;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatCommandService implements ChatCommandUseCase {

    private final OpenAiService openAiService;
    private final ChatCommandPort chatCommandPort;
    private final ProblemQueryPort problemQueryPort;

    @Value("${chat-gpt.model}")
    private String model;

    @Value("${chat-gpt.role}")
    private String role;

    @Override
    public ChatGPTCompletionResponseDto send(AuthUser authUser, Long problemId, ChatGPTCompletionRequestDto requestDto){
        Problem problem = problemQueryPort.findProblemById(problemId)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
                createChatCompletion(requestDto,problem));

        ChatGPTCompletionResponseDto responseDto = ChatGPTCompletionResponseDto.from(chatCompletion);

        chatCommandPort.save(Chat.of(authUser.getUserId(), problemId, requestDto.getMessage(),
                responseDto.getMessages().get(0).getMessage()));
        return responseDto;
    }

    private ChatCompletionRequest createChatCompletion(ChatGPTCompletionRequestDto requestDto, Problem problem){
        return ChatCompletionRequest.builder()
                .model(model)
                .messages(convertChatMessage(requestDto, problem))
                .build();
    }

    private List<ChatMessage> convertChatMessage(ChatGPTCompletionRequestDto requestDto, Problem problem) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage("system", "유저가 " + problem.getQuestion() + " 라는 질문에 " +
                problem.getAnswer() + " 라고 답을 한 상황이다. " + "앞으로 유저가 질문할 때 이 상황을 기반으로 너는 IT 전문가로서 5줄 이내로 대답을 해야한다."));
        chatMessages.add(new ChatMessage(role, requestDto.getMessage()));
        return chatMessages;
    }


}

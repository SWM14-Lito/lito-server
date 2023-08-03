package com.swm.lito.core.chat.application.service;

import com.swm.lito.core.chat.application.port.ChatCommandPort;
import com.swm.lito.core.chat.application.port.in.ChatCommandUseCase;
import com.swm.lito.core.chat.application.port.in.request.ChatGPTCompletionRequestDto;
import com.swm.lito.core.chat.application.port.in.response.ChatGPTCompletionResponseDto;
import com.swm.lito.core.chat.domain.Chat;
import com.swm.lito.core.common.security.AuthUser;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatCommandService implements ChatCommandUseCase {

    private final OpenAiService openAiService;
    private final ChatCommandPort chatCommandPort;

    @Override
    public ChatGPTCompletionResponseDto send(AuthUser authUser, Long problemId, ChatGPTCompletionRequestDto requestDto){
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
                ChatGPTCompletionRequestDto.from(requestDto));

        ChatGPTCompletionResponseDto responseDto = ChatGPTCompletionResponseDto.from(chatCompletion);

        //유저 번호, 문제 번호 까지 합쳐서 저장
        chatCommandPort.save(Chat.of(authUser.getUserId(), problemId, requestDto.getMessage(),
                responseDto.getMessages().get(0).getMessage()));
        return responseDto;
    }


}

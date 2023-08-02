package com.swm.lito.core.chat.application.service;

import com.swm.lito.core.chat.application.port.in.ChatCommandUseCase;
import com.swm.lito.core.chat.application.port.in.request.ChatGPTCompletionRequestDto;
import com.swm.lito.core.chat.application.port.in.response.ChatGPTCompletionResponseDto;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatCommandService implements ChatCommandUseCase {

    private final OpenAiService openAiService;

    @Override
    public ChatGPTCompletionResponseDto send(ChatGPTCompletionRequestDto requestDto){
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
                ChatGPTCompletionRequestDto.from(requestDto));

        ChatGPTCompletionResponseDto responseDto = ChatGPTCompletionResponseDto.from(chatCompletion);

        return responseDto;
    }


}

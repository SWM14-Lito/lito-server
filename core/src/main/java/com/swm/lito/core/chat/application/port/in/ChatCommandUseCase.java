package com.swm.lito.core.chat.application.port.in;

import com.swm.lito.core.chat.application.port.in.request.ChatGPTCompletionRequestDto;
import com.swm.lito.core.chat.application.port.in.response.ChatGPTCompletionResponseDto;

public interface ChatCommandUseCase {

    ChatGPTCompletionResponseDto send(ChatGPTCompletionRequestDto requestDto);
}

package com.lito.core.chat.application.port.in;

import com.lito.core.chat.application.port.in.request.ChatGPTCompletionRequestDto;
import com.lito.core.chat.application.port.in.response.ChatGPTCompletionResponseDto;
import com.lito.core.common.security.AuthUser;

public interface ChatCommandUseCase {

    ChatGPTCompletionResponseDto send(AuthUser authUser, Long problemId, ChatGPTCompletionRequestDto requestDto);
}

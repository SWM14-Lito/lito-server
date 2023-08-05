package com.swm.lito.api.chat.adapter.in.web;

import com.swm.lito.api.chat.adapter.in.request.ChatGPTCompletionRequest;
import com.swm.lito.api.chat.adapter.in.response.ChatGPTCompletionResponse;
import com.swm.lito.core.chat.application.port.in.ChatCommandUseCase;
import com.swm.lito.core.common.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat-gpt")
@RequiredArgsConstructor
public class ChatController {

    private final ChatCommandUseCase chatCommandUseCase;

    @PostMapping("/{problemId}")
    public ChatGPTCompletionResponse send(@AuthenticationPrincipal AuthUser authUser,
                                          @PathVariable Long problemId,
                                          @RequestBody ChatGPTCompletionRequest request){
        return ChatGPTCompletionResponse.from(chatCommandUseCase.send(authUser, problemId, request.toRequestDto()));
    }
}

package com.swm.lito.api.chat.adapter.in.presentation;

import com.swm.lito.api.chat.adapter.in.request.ChatGPTCompletionRequest;
import com.swm.lito.api.chat.adapter.in.response.ChatGPTCompletionResponse;
import com.swm.lito.core.chat.application.port.in.ChatCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat-gpt")
@RequiredArgsConstructor
public class ChatController {

    private final ChatCommandUseCase chatCommandUseCase;

    @PostMapping
    public ChatGPTCompletionResponse send(@RequestBody ChatGPTCompletionRequest request){
        return ChatGPTCompletionResponse.from(chatCommandUseCase.send(request.toRequestDto()));
    }
}

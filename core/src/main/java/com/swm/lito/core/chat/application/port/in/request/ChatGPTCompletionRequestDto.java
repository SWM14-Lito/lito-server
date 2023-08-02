package com.swm.lito.core.chat.application.port.in.request;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatGPTCompletionRequestDto {

    private String model;

    private String role;

    private String message;

    private Integer maxTokens;

    public static ChatCompletionRequest from(ChatGPTCompletionRequestDto request) {
        return ChatCompletionRequest.builder()
                .model(request.getModel())
                .messages(convertChatMessage(request))
                .maxTokens(request.getMaxTokens())
                .build();
    }

    private static List<ChatMessage> convertChatMessage(ChatGPTCompletionRequestDto request) {
        return List.of(new ChatMessage(request.getRole(), request.getMessage()));
    }
}

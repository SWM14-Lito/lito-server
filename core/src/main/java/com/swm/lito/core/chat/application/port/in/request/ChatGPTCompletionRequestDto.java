package com.swm.lito.core.chat.application.port.in.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatGPTCompletionRequestDto {

    private String model;

    private String role;

    private String message;

    public static ChatGPTCompletionRequestDto from(String message){
        return ChatGPTCompletionRequestDto.builder()
                .message(message)
                .build();
    }

    public static ChatGPTCompletionRequestDto from(String model, String role, String message){
        return ChatGPTCompletionRequestDto.builder()
                .model(model)
                .role(role)
                .message(message)
                .build();
    }

}

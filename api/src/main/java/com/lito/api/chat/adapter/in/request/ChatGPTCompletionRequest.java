package com.lito.api.chat.adapter.in.request;

import com.lito.core.chat.application.port.in.request.ChatGPTCompletionRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatGPTCompletionRequest {

    private String message;


    public ChatGPTCompletionRequestDto toRequestDto(){
        return ChatGPTCompletionRequestDto.from(message);
    }
}

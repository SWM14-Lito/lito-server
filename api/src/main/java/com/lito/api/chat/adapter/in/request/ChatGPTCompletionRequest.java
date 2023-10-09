package com.lito.api.chat.adapter.in.request;

import com.lito.core.chat.application.port.in.request.ChatGPTCompletionRequestDto;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChatGPTCompletionRequest {

    private String message;


    public ChatGPTCompletionRequestDto toRequestDto(){
        return ChatGPTCompletionRequestDto.from(message);
    }
}

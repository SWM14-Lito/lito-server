package com.swm.lito.api.chat.adapter.in.request;

import com.swm.lito.core.chat.application.port.in.request.ChatGPTCompletionRequestDto;
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
public class ChatGPTCompletionRequest {

    private String message;


    public ChatGPTCompletionRequestDto toRequestDto(){
        return ChatGPTCompletionRequestDto.from(message);
    }
}

package com.lito.api.chat.adapter.in.response;

import com.lito.core.chat.application.port.in.response.ChatGPTCompletionResponseDto;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatGPTCompletionResponse {

    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Message> messages;

    private Usage usage;

    public static ChatGPTCompletionResponse from(ChatGPTCompletionResponseDto responseDto){
        return ChatGPTCompletionResponse.builder()
                .id(responseDto.getId())
                .object(responseDto.getObject())
                .created(responseDto.getCreated())
                .model(responseDto.getModel())
                .messages(Message.from(responseDto.getMessages()))
                .usage(Usage.from(responseDto.getUsage()))
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Message {

        private String role;

        private String message;

        public static Message from(ChatMessage chatMessage) {
            return Message.builder()
                    .message(chatMessage.getRole())
                    .message(chatMessage.getContent())
                    .build();
        }

        public static List<Message> from(List<ChatGPTCompletionResponseDto.Message> messages){
            return messages.stream()
                    .map(Message::from)
                    .collect(Collectors.toList());
        }

        public static Message from(ChatGPTCompletionResponseDto.Message message){
            return Message.builder()
                    .role(message.getRole())
                    .message(message.getMessage())
                    .build();
        }
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Usage {

        private Long promptTokens;

        private Long completionTokens;

        private Long totalTokens;

        public static Usage from(ChatGPTCompletionResponseDto.Usage usage){
            return Usage.builder()
                    .promptTokens(usage.getPromptTokens())
                    .completionTokens(usage.getCompletionTokens())
                    .totalTokens(usage.getTotalTokens())
                    .build();
        }
    }
}

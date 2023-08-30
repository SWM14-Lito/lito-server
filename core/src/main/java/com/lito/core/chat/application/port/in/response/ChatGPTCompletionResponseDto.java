package com.lito.core.chat.application.port.in.response;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
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
public class ChatGPTCompletionResponseDto {

    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Message> messages;

    private Usage usage;

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
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Usage {

        private Long promptTokens;

        private Long completionTokens;

        private Long totalTokens;

        public static Usage from(com.theokanning.openai.Usage usage) {
            return Usage.builder()
                    .promptTokens(usage.getPromptTokens())
                    .completionTokens(usage.getCompletionTokens())
                    .totalTokens(usage.getTotalTokens())
                    .build();
        }
    }

    public static List<Message> toResponseDtoMessages(List<ChatCompletionChoice> choices) {
        return choices.stream()
                .map(completionChoice -> Message.from(completionChoice.getMessage()))
                .collect(Collectors.toList());
    }

    public static ChatGPTCompletionResponseDto from(ChatCompletionResult result) {
        return ChatGPTCompletionResponseDto.builder()
                .id(result.getId())
                .object(result.getObject())
                .created(result.getCreated())
                .model(result.getModel())
                .messages(toResponseDtoMessages(result.getChoices()))
                .usage(Usage.from(result.getUsage()))
                .build();
    }
}

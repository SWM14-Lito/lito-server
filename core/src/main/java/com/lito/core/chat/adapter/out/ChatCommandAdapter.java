package com.lito.core.chat.adapter.out;

import com.lito.core.chat.application.port.ChatCommandPort;
import com.lito.core.chat.domain.Chat;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatCommandAdapter implements ChatCommandPort {

    private final OpenAiService openAiService;
    private final ChatRepository chatRepository;

    @Override
    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    @Override
    public ChatCompletionResult createChatCompletion(ChatCompletionRequest chatCompletionRequest) {
        return openAiService.createChatCompletion(chatCompletionRequest);
    }
}

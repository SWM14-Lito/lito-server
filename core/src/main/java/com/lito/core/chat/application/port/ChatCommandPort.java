package com.lito.core.chat.application.port;

import com.lito.core.chat.domain.Chat;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;

public interface ChatCommandPort {

    void save(Chat chat);

    ChatCompletionResult createChatCompletion(ChatCompletionRequest chatCompletionRequest);
}

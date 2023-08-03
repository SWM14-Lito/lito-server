package com.swm.lito.core.chat.adapter.out;

import com.swm.lito.core.chat.application.port.ChatCommandPort;
import com.swm.lito.core.chat.domain.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatCommandAdapter implements ChatCommandPort {

    private final ChatRepository chatRepository;

    @Override
    public void save(Chat chat) {
        chatRepository.save(chat);
    }
}

package com.lito.core.chat.application.port;

import com.lito.core.chat.domain.Chat;

public interface ChatCommandPort {

    void save(Chat chat);
}

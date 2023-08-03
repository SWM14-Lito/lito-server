package com.swm.lito.core.chat.application.port;

import com.swm.lito.core.chat.domain.Chat;

public interface ChatCommandPort {

    void save(Chat chat);
}

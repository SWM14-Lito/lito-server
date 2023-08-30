package com.lito.core.chat.adapter.out;

import com.lito.core.chat.domain.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ChatRepository extends MongoRepository<Chat, String> {
}

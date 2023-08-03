package com.swm.lito.core.chat.adapter.out;

import com.swm.lito.core.chat.domain.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ChatRepository extends MongoRepository<Chat, String> {
}

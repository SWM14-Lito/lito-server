package com.swm.lito.core.chat.domain;

import com.swm.lito.core.common.entity.BaseEntity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "chats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Chat extends BaseEntity {

    @Id
    private String id;

    private Long userId;

    private Long problemId;

    private String question;

    private String answer;

    public static Chat of(Long userId, Long problemId, String question, String answer){
        return Chat.builder()
                .userId(userId)
                .problemId(problemId)
                .question(question)
                .answer(answer)
                .build();
    }

}

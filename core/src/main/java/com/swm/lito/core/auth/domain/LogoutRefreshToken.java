package com.swm.lito.core.auth.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("logoutRefreshToken")
@Getter
@AllArgsConstructor
@Builder
public class LogoutRefreshToken {

    @Id
    private String id;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    public static LogoutRefreshToken of(String refreshToken, long expiration) {
        return LogoutRefreshToken.builder()
                .id(refreshToken)
                .expiration(expiration)
                .build();
    }
}

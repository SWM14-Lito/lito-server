package com.swm.lito.auth.adapter.out.persistence;

import com.swm.lito.auth.domain.LogoutRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutRefreshTokenRedisRepository extends CrudRepository<LogoutRefreshToken, String> {
}
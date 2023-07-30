package com.swm.lito.core.auth.adapter.out.persistence;

import com.swm.lito.core.auth.domain.LogoutRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutRefreshTokenRedisRepository extends CrudRepository<LogoutRefreshToken, String> {
}

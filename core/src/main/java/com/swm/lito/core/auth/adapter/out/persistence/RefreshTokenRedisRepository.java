package com.swm.lito.core.auth.adapter.out.persistence;

import com.swm.lito.core.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}

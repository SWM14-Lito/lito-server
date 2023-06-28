package com.swm.lito.auth.adapter.out.persistence;

import com.swm.lito.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}

package com.swm.lito.core.auth.adapter.out.persistence;

import com.swm.lito.core.auth.domain.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
}

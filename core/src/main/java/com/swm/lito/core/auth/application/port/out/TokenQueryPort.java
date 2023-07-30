package com.swm.lito.core.auth.application.port.out;

import com.swm.lito.core.auth.domain.RefreshToken;

import java.util.Optional;

public interface TokenQueryPort {

    Optional<RefreshToken> findRefreshTokenByUsername(String username);

    boolean existsLogoutAccessTokenById(String id);

    boolean existsLogoutRefreshTokenById(String id);

    boolean existsRefreshTokenByUsername(String username);
}

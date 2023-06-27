package com.swm.lito.auth.application.port.out;

import com.swm.lito.auth.domain.RefreshToken;

import java.util.Optional;

public interface TokenQueryPort {

    Optional<RefreshToken> findRefreshTokenByUsername(String username);

    boolean existsLogoutAccessTokenById(String id);

    boolean existsLogoutRefreshTokenById(String id);

    boolean existsRefreshTokenByUsername(String username);
}

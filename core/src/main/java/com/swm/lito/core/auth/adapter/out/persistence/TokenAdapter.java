package com.swm.lito.core.auth.adapter.out.persistence;

import com.swm.lito.core.auth.application.port.out.TokenCommandPort;
import com.swm.lito.core.auth.application.port.out.TokenQueryPort;
import com.swm.lito.core.auth.domain.LogoutAccessToken;
import com.swm.lito.core.auth.domain.LogoutRefreshToken;
import com.swm.lito.core.auth.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenAdapter implements TokenQueryPort, TokenCommandPort {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final LogoutRefreshTokenRedisRepository logoutRefreshTokenRedisRepository;

    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshTokenRedisRepository.save(refreshToken);
    }

    @Override
    public void saveLogoutAccessToken(LogoutAccessToken logoutAccessToken) {
        logoutAccessTokenRedisRepository.save(logoutAccessToken);
    }

    @Override
    public void saveLogoutRefreshToken(LogoutRefreshToken logoutRefreshToken) {
        logoutRefreshTokenRedisRepository.save(logoutRefreshToken);
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenByUsername(String username) {
        return refreshTokenRedisRepository.findById(username);
    }

    @Override
    public boolean existsLogoutAccessTokenById(String id) {
        return logoutAccessTokenRedisRepository.existsById(id);
    }

    @Override
    public boolean existsLogoutRefreshTokenById(String id) {
        return logoutRefreshTokenRedisRepository.existsById(id);
    }

    @Override
    public boolean existsRefreshTokenByUsername(String username) {
        return refreshTokenRedisRepository.existsById(username);
    }

    @Override
    public void deleteRefreshToken(RefreshToken refreshToken){
        refreshTokenRedisRepository.delete(refreshToken);
    }
}

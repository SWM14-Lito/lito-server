package com.lito.core.auth.application.port.out;

import com.lito.core.auth.domain.LogoutAccessToken;
import com.lito.core.auth.domain.LogoutRefreshToken;
import com.lito.core.auth.domain.RefreshToken;

public interface TokenCommandPort {

    void saveRefreshToken(RefreshToken refreshToken);

    void saveLogoutAccessToken(LogoutAccessToken logoutAccessToken);

    void saveLogoutRefreshToken(LogoutRefreshToken logoutRefreshToken);
}

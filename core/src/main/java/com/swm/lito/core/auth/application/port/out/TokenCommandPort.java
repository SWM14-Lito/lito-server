package com.swm.lito.core.auth.application.port.out;

import com.swm.lito.core.auth.domain.LogoutAccessToken;
import com.swm.lito.core.auth.domain.LogoutRefreshToken;
import com.swm.lito.core.auth.domain.RefreshToken;

public interface TokenCommandPort {

    void saveRefreshToken(RefreshToken refreshToken);

    void saveLogoutAccessToken(LogoutAccessToken logoutAccessToken);

    void saveLogoutRefreshToken(LogoutRefreshToken logoutRefreshToken);

    void deleteRefreshToken(RefreshToken refreshToken);
}

package com.swm.lito.auth.application.port.out;

import com.swm.lito.auth.domain.LogoutAccessToken;
import com.swm.lito.auth.domain.LogoutRefreshToken;
import com.swm.lito.auth.domain.RefreshToken;

public interface TokenCommandPort {

    void saveRefreshToken(RefreshToken refreshToken);

    void saveLogoutAccessToken(LogoutAccessToken logoutAccessToken);

    void saveLogoutRefreshToken(LogoutRefreshToken logoutRefreshToken);

    void deleteRefreshToken(RefreshToken refreshToken);
}

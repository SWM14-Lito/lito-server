package com.swm.lito.auth.application.port.out;

import com.swm.lito.auth.adapter.out.oauth.OauthUserInfo;
import com.swm.lito.user.domain.enums.Provider;

public interface OauthPort {

    OauthUserInfo getUserInfo(Provider provider, String accessToken);
}

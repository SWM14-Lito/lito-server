package com.swm.lito.auth.adapter.out.oauth;

import com.swm.lito.user.domain.enums.Provider;

public interface OauthRequester {

    boolean supports(Provider provider);

    OauthUserInfo getUserInfo(String accessToken);
}

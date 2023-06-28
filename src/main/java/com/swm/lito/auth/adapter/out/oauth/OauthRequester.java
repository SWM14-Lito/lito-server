package com.swm.lito.auth.adapter.out.oauth;

import com.swm.lito.auth.domain.oauth.OauthUserInfo;
import com.swm.lito.user.domain.enums.Provider;

public interface OauthRequester {

    boolean supports(Provider provider);

    OauthUserInfo getUserInfoByCode(String code);
}

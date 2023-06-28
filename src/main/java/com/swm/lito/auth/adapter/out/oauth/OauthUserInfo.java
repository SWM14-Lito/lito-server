package com.swm.lito.auth.adapter.out.oauth;

import com.swm.lito.user.domain.enums.Provider;

public interface OauthUserInfo {

    String getEmail();
    String getName();
    Provider getProvider();
}

package com.swm.lito.auth.adapter.out.oauth;

import com.swm.lito.auth.domain.oauth.OauthUserInfo;
import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.user.domain.enums.Provider;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.swm.lito.common.exception.infrastructure.InfraErrorCode.INVALID_OAUTH;

@Component
public class OauthHandler {

    private final List<OauthRequester> oauthRequesters;

    public OauthHandler(final List<OauthRequester> oauthRequesters) {
        this.oauthRequesters = oauthRequesters;
    }

    public OauthUserInfo getUserInfoFromCode(Provider provider, String code) {
        OauthRequester requester = getRequester(provider);
        return requester.getUserInfoByCode(code);
    }

    private OauthRequester getRequester(Provider provider) {
        return oauthRequesters.stream()
                .filter(requester -> requester.supports(provider))
                .findFirst()
                .orElseThrow(()-> new ApplicationException(INVALID_OAUTH));
    }
}

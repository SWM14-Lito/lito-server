package com.swm.lito.auth.adapter.out.oauth;

import com.swm.lito.auth.application.port.out.OauthPort;
import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.user.domain.enums.Provider;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.swm.lito.common.exception.infrastructure.InfraErrorCode.INVALID_OAUTH;

@Component
public class OauthAdapter implements OauthPort {

    private final List<OauthRequester> oauthRequesters;

    public OauthAdapter(final List<OauthRequester> oauthRequesters) {
        this.oauthRequesters = oauthRequesters;
    }

    public OauthUserInfo getUserInfo(Provider provider, String accessToken) {
        OauthRequester requester = getRequester(provider);
        return requester.getUserInfo(accessToken);
    }

    private OauthRequester getRequester(Provider provider) {
        return oauthRequesters.stream()
                .filter(requester -> requester.supports(provider))
                .findFirst()
                .orElseThrow(()-> new ApplicationException(INVALID_OAUTH));
    }
}

package com.swm.lito.auth.adapter.out.oauth;

import com.swm.lito.user.domain.enums.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoOauthUserInfo implements OauthUserInfo{

    private Map<String, Object> attributes;
    private Map<String, Object> attributesAccount;
    private Map<String, Object> attributesProfile;

    private KakaoOauthUserInfo(Map<String, Object> attributes){
        this.attributes = Collections.unmodifiableMap(attributes);
        this.attributesAccount = Collections.unmodifiableMap((Map<String, Object>) attributes.get("kakao_account"));
        this.attributesProfile= Collections.unmodifiableMap((Map<String, Object>) attributesAccount.get("profile"));
    }

    public static KakaoOauthUserInfo from(Map<String, Object> attributes){
        return new KakaoOauthUserInfo(attributes);
    }

    @Override
    public String getEmail() {
        return attributesAccount.get("email").toString();
    }

    @Override
    public String getName() {
        return attributesAccount.get("name").toString();
    }

    @Override
    public Provider getProvider() {
        return Provider.KAKAO;
    }
}

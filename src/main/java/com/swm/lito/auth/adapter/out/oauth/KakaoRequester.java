package com.swm.lito.auth.adapter.out.oauth;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.user.domain.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Map;

import static com.swm.lito.common.exception.auth.AuthErrorCode.KAKAO_LOGIN;

@Component
@RequiredArgsConstructor
public class KakaoRequester implements OauthRequester{

    @Value("${spring.kakao.profile}")
    private String PROFILE_URL;

    private final WebClient kakaoUserClient;

    @Override
    public boolean supports(Provider provider) {
        return provider.isSameAs(Provider.KAKAO);
    }

    @Override
    public KakaoOauthUserInfo getUserInfo(String accessToken){
        Map<String, Object> responseBody = requestKaKaoClient(kakaoUserClient)
                .get()
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .timeout(Duration.ofSeconds(8))
                .blockOptional()
                .orElseThrow(()->new ApplicationException(KAKAO_LOGIN));

        return KakaoOauthUserInfo.from(responseBody);

    }

    private WebClient requestKaKaoClient(WebClient webClient) {
        return webClient.mutate()
                .baseUrl(PROFILE_URL)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

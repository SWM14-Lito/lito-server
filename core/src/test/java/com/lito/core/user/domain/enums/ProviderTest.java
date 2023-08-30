package com.lito.core.user.domain.enums;

import com.lito.core.common.exception.ApplicationException;
import com.lito.core.user.domain.enums.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.lito.core.common.exception.auth.AuthErrorCode.INVALID_OAUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("Provider")
class ProviderTest {

    @Nested
    @DisplayName("toEnum 메서드는")
    class to_enum{

        @Nested
        @DisplayName("문자열 provider 값을 가지고")
        class with_string_provider{

            String provider = "kakao";

            @Test
            @DisplayName("provider enum값을 리턴한다.")
            void it_returns_provider_enum() {

                assertThat(Provider.toEnum(provider)).isEqualTo(Provider.KAKAO);
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않는 oauth 라면")
        class with_invalid_oauth{

            String provider = "naver";

            @Test
            @DisplayName("INVALID_OAUTH 예외를 발생시킨다.")
            void it_throws_invalid_oauth() {

                assertThatThrownBy(() -> Provider.toEnum(provider))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(INVALID_OAUTH.getMessage());
            }
        }
    }
}
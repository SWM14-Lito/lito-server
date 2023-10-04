package com.lito.core.user.adapter.out.persistence;

import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("UserQueryAdapter")
@Transactional
class UserQueryAdapterTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserQueryAdapter userQueryAdapter;

    User user = User.builder()
            .oauthId("kakaoId")
            .email("test@test.com")
            .nickname("simple")
            .provider(Provider.KAKAO)
            .build();

    Long id;

    @BeforeEach
    void setUp(){
        id = userRepository.save(user).getId();
    }

    @Nested
    @DisplayName("findByEmail 메서드는")
    class find_by_email{

        @Nested
        @DisplayName("email을 가지고")
        class with_email{

            String email = user.getEmail();

            @Test
            @DisplayName("조회된 유저를 리턴한다.")
            void it_returns_user() {

                User user = userQueryAdapter.findByEmail(email).get();

                assertThat(user.getEmail()).isEqualTo(email);
            }
        }
    }

    @Nested
    @DisplayName("findByNickname 메서드는")
    class find_by_nickname{

        @Nested
        @DisplayName("nickname을 가지고")
        class with_nickname{


            String nickname = user.getNickname();

            @Test
            @DisplayName("조회된 유저를 리턴한다.")
            void it_returns_user() {

                User user = userQueryAdapter.findByNickname(nickname).get();

                assertThat(user.getNickname()).isEqualTo(nickname);
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class find_by_id{

        @Nested
        @DisplayName("id를 가지고")
        class with_id{

            @Test
            @DisplayName("조회된 유저를 리턴한다.")
            void it_returns_user() {

                User user = userQueryAdapter.findById(id).get();

                assertThat(user.getId()).isEqualTo(id);
            }
        }
    }

    @Nested
    @DisplayName("findByOauthIdAndProvider 메서드는")
    class find_by_oauth_id_and_provider{

        @Nested
        @DisplayName("oauthId와 provider를 가지고")
        class with_oauth_id_provider{

            String oauthId = user.getOauthId();
            Provider provider = user.getProvider();

            @Test
            @DisplayName("조회된 유저를 리턴한다.")
            void it_returns_user() {

                User user = userQueryAdapter.findByOauthIdAndProvider(oauthId, provider).get();

                assertThat(user.getOauthId()).isEqualTo(oauthId);
                assertThat(user.getProvider()).isEqualTo(provider);
            }
        }
    }

    @Nested
    @DisplayName("findByEmailAndProvider 메서드는")
    class find_by_email_and_provider{

        @Nested
        @DisplayName("email, provider를 가지고")
        class with_email_provider{

            String email = user.getEmail();
            Provider provider = user.getProvider();

            @Test
            @DisplayName("조회된 유저를 리턴한다.")
            void it_returns_user() throws Exception{

                User user = userQueryAdapter.findByEmailAndProvider(email, provider).get();

                assertThat(user.getEmail()).isEqualTo(email);
                assertThat(user.getProvider()).isEqualTo(provider);
            }
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class find_all{
        @Test
        @DisplayName("모든 유저를 리턴한다.")
        void it_returns_all_user() throws Exception{

            List<User> users = userQueryAdapter.findAll();

            assertThat(users.size()).isEqualTo(1);
        }
    }
}
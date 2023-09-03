package com.lito.core.user.application.service;

import com.lito.core.common.entity.BaseEntity;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.security.AuthUser;
import com.lito.core.user.adapter.out.persistence.UserRepository;
import com.lito.core.user.application.port.in.request.ProfileRequestDto;
import com.lito.core.user.application.port.in.request.UserRequestDto;
import com.lito.core.user.application.service.UserCommandService;
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

import static com.lito.core.common.exception.user.UserErrorCode.USER_EXISTED_NICKNAME;
import static com.lito.core.common.exception.user.UserErrorCode.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("UserCommandService")
class UserCommandServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCommandService userCommandService;

    User user = User.builder()
            .oauthId("kakaoId")
            .email("test@test.com")
            .provider(Provider.KAKAO)
            .build();
    AuthUser authUser = AuthUser.of(user);

    User notFoundUser = User.builder()
            .id(999L)
            .oauthId("appleId")
            .email("apple@icloud.com")
            .provider(Provider.APPLE)
            .build();
    AuthUser notFoundAuthUser = AuthUser.of(notFoundUser);

    @Nested
    @DisplayName("create 메서드는")
    class create{

        UserRequestDto userRequestDto = UserRequestDto.builder()
                .nickname("심플")
                .name("석환")
                .introduce("소개")
                .build();

        @Nested
        @DisplayName("authUser와 userRequestDto를 가지고")
        class with_auth_user_user_request_dto{

            @BeforeEach
            void setUp(){
                userRepository.save(user);
            }

            @Test
            @DisplayName("유저 프로필을 생성한다.")
            void it_creates_user_profile() {

                assertThatCode(() -> userCommandService.create(authUser, userRequestDto))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 유저라면")
        class with_user_not_found{

            @Test
            @DisplayName("USER_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_user_not_found() {

                assertThatThrownBy(() -> userCommandService.create(notFoundAuthUser, userRequestDto))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(USER_NOT_FOUND.getMessage());
            }
        }

        @Nested
        @DisplayName("만약 이미 존재하는 닉네임을 입력하면")
        class with_existed_nickname{
            User user = User.builder()
                    .oauthId("kakaoId")
                    .email("test@test.com")
                    .provider(Provider.KAKAO)
                    .nickname("심플")
                    .name("석환")
                    .introduce("소개")
                    .build();

            User user2 = User.builder()
                    .oauthId("appleId")
                    .email("jobs@icloud.com")
                    .provider(Provider.APPLE)
                    .build();

            AuthUser authUser2 = AuthUser.of(user2);

            @BeforeEach
            void setUp(){
                userRepository.saveAll(List.of(user,user2));
            }

            UserRequestDto otherUserRequestDto = UserRequestDto.builder()
                    .nickname("심플")
                    .name("잡스")
                    .introduce("소개")
                    .build();

            @Test
            @DisplayName("USER_EXISTED_NICKNAME 예외를 발생시킨다.")
            void it_throws_user_existed_nickname() {

                assertThatThrownBy(() -> userCommandService.create(authUser2, otherUserRequestDto))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(USER_EXISTED_NICKNAME.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class update{

        ProfileRequestDto profileRequestDto = ProfileRequestDto.builder()
                .nickname("심플")
                .introduce("소개")
                .build();

        @Nested
        @DisplayName("authUser와 profileRequestDto를 가지고")
        class with_auth_user_profile_request_dto{

            @BeforeEach
            void setUp(){
                userRepository.save(user);
            }

            @Test
            @DisplayName("유저 프로필을 수정한다.")
            void it_updates_user_profile() {

                assertThatCode(() -> userCommandService.update(authUser, profileRequestDto))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 유저라면")
        class with_user_not_found{

            @Test
            @DisplayName("USER_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_user_not_found() {

                assertThatThrownBy(() -> userCommandService.update(notFoundAuthUser, profileRequestDto))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(USER_NOT_FOUND.getMessage());
            }
        }

        @Nested
        @DisplayName("만약 이미 존재하는 닉네임을 입력하면")
        class with_existed_nickname{

            User user = User.builder()
                    .oauthId("kakaoId")
                    .email("test@test.com")
                    .provider(Provider.KAKAO)
                    .nickname("심플")
                    .name("석환")
                    .introduce("소개")
                    .build();

            User user2 = User.builder()
                    .oauthId("appleId")
                    .email("jobs@icloud.com")
                    .provider(Provider.APPLE)
                    .build();

            AuthUser authUser2 = AuthUser.of(user2);

            @BeforeEach
            void setUp(){
                userRepository.saveAll(List.of(user,user2));
            }

            ProfileRequestDto profileRequestDto = ProfileRequestDto.builder()
                    .nickname("심플")
                    .introduce("하이")
                    .build();

            @Test
            @DisplayName("USER_EXISTED_NICKNAME 예외를 발생시킨다.")
            void it_throws_user_existed_nickname() {

                assertThatThrownBy(() -> userCommandService.update(authUser2, profileRequestDto))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(USER_EXISTED_NICKNAME.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("updateNotification 메서드는")
    class update_notification{

        String alarmStatus = "Y";

        @Nested
        @DisplayName("authUser, alarmStatus를 가지고")
        class with_auth_user_alarm_status{

            @BeforeEach
            void setUp(){
                userRepository.save(user);
            }

            @Test
            @DisplayName("알람 상태를 변경한다.")
            void it_changes_alarm_status() throws Exception{

                userCommandService.updateNotification(authUser, alarmStatus);

                assertThat(user.getAlarmStatus()).isEqualTo(alarmStatus);
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 유저라면")
        class with_user_not_found{

            @Test
            @DisplayName("USER_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_user_not_found() throws Exception{

                assertThatThrownBy(() -> userCommandService.updateNotification(notFoundAuthUser, alarmStatus))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(USER_NOT_FOUND.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class delete{

        @Nested
        @DisplayName("authUser를 가지고")
        class with_auth_user{

            @BeforeEach
            void setUp(){
                userRepository.save(user);
            }

            @Test
            @DisplayName("user의 status를 INACTIVE로 변경한다.")
            void it_changes_status_in_inactive() throws Exception{

                userCommandService.delete(authUser);

                assertThat(user.getStatus()).isEqualTo(BaseEntity.Status.INACTIVE);
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 유저라면")
        class with_user_not_found{

            @Test
            @DisplayName("USER_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_user_not_found() throws Exception{

                assertThatThrownBy(() -> userCommandService.delete(notFoundAuthUser))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(USER_NOT_FOUND.getMessage());

            }
        }
    }


}
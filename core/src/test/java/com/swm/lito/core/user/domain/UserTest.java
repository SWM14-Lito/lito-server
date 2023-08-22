package com.swm.lito.core.user.domain;

import com.swm.lito.core.common.entity.BaseEntity;
import com.swm.lito.core.user.application.port.in.request.ProfileRequestDto;
import com.swm.lito.core.user.application.port.in.request.UserRequestDto;
import com.swm.lito.core.user.domain.enums.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User")
@ActiveProfiles("test")
@SpringBootTest
class UserTest {

    User user = User.builder()
            .oauthId("kakaoId")
            .email("test@test.com")
            .provider(Provider.KAKAO)
            .profileImgUrl("imageUrl")
            .build();

    @Nested
    @DisplayName("create 메서드는")
    class create{
        User user2 = User.builder()
                .oauthId("kakaoId")
                .email("test@test.com")
                .provider(Provider.KAKAO)
                .nickname("심플")
                .introduce("소개")
                .name("석환")
                .build();

        @Nested
        @DisplayName("userRequestDto를 가지고")
        class with_user_request_dto{

            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .nickname("심플")
                    .introduce("소개")
                    .name("석환")
                    .build();

            @Test
            @DisplayName("nickname, introduce, name을 생성한다.")
            void it_creates_nickname_and_introduce_and_name() {

                user.create(userRequestDto);

                assertAll(
                        () -> assertThat(user.getNickname()).isEqualTo(userRequestDto.getNickname()),
                        () -> assertThat(user.getIntroduce()).isEqualTo(userRequestDto.getIntroduce()),
                        () -> assertThat(user.getName()).isEqualTo(userRequestDto.getName())
                );
            }
        }

        @Nested
        @DisplayName("만약 nickname이 null 이라면")
        class with_nickname_null{

            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .introduce("소개")
                    .name("석환")
                    .build();

            @Test
            @DisplayName("nickname은 변경하지 않는다.")
            void it_does_not_change_nickname() {

                user2.create(userRequestDto);

                assertThat(user2.getNickname()).isNotNull();
            }
        }

        @Nested
        @DisplayName("만약 nickname이 공백이라면")
        class with_nickname_white_space{

            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .nickname(" ")
                    .introduce("소개")
                    .name("석환")
                    .build();

            @Test
            @DisplayName("nickname은 변경하지 않는다.")
            void it_does_not_change_nickname() {

                user2.create(userRequestDto);

                assertThat(user2.getNickname()).isNotEqualTo(" ");
            }
        }

        @Nested
        @DisplayName("만약 nickname이 아무 값이 없다면")
        class with_nickname_no_value{

            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .nickname("")
                    .introduce("소개")
                    .name("석환")
                    .build();

            @Test
            @DisplayName("nickname은 변경하지 않는다.")
            void it_does_not_change_nickname() {

                user2.create(userRequestDto);

                assertThat(user2.getNickname()).isNotBlank();
            }
        }

        @Nested
        @DisplayName("만약 introduce가 null이라면")
        class with_introduce_null{

            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .nickname("심플")
                    .name("석환")
                    .build();

            @Test
            @DisplayName("introduce를 변경하지 않는다.")
            void it_does_not_change_introduce() {

                user2.create(userRequestDto);

                assertThat(user2.getIntroduce()).isNotNull();
            }
        }

        @Nested
        @DisplayName("만약 name이 null 이라면")
        class with_name_null{

            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .nickname("심플")
                    .introduce("소개")
                    .build();

            @Test
            @DisplayName("name을 변경하지 않는다.")
            void it_does_not_change_name() {

                user2.create(userRequestDto);

                assertThat(user2.getName()).isNotNull();
            }
        }

        @Nested
        @DisplayName("만약 name이 공백이라면")
        class with_name_white_space{

            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .nickname("심플")
                    .introduce("소개")
                    .name(" ")
                    .build();

            @Test
            @DisplayName("name을 변경하지 않는다.")
            void it_does_not_change_name() {

                user2.create(userRequestDto);

                assertThat(user2.getName()).isNotEqualTo(" ");
            }
        }

        @Nested
        @DisplayName("만약 name이 값이 없다면")
        class with_name_no_value{

            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .nickname("심플")
                    .introduce("소개")
                    .name("")
                    .build();

            @Test
            @DisplayName("name을 변경하지 않는다.")
            void it_does_not_change_name() {

                user2.create(userRequestDto);

                assertThat(user2.getName()).isNotBlank();
            }
        }
    }


    @Nested
    @DisplayName("change 메서드는")
    class change{

        User user2 = User.builder()
                .oauthId("kakaoId")
                .email("test@test.com")
                .provider(Provider.KAKAO)
                .nickname("심플")
                .introduce("소개")
                .build();

        @Nested
        @DisplayName("profileRequestDto를 가지고")
        class with_profile_request_dto{

            ProfileRequestDto profileRequestDto = ProfileRequestDto.builder()
                    .nickname("잡스")
                    .introduce("하이")
                    .build();

            @Test
            @DisplayName("nickname, introduce를 수정한다.")
            void it_changes_nickname_and_introduce() {

                user.change(profileRequestDto);

                assertThat(user.getNickname()).isEqualTo(profileRequestDto.getNickname());
                assertThat(user.getIntroduce()).isEqualTo(profileRequestDto.getIntroduce());
            }
        }

        @Nested
        @DisplayName("만약 nickname이 null 이라면")
        class with_nickname_null{

            ProfileRequestDto profileRequestDto = ProfileRequestDto.builder()
                    .introduce("하이")
                    .build();

            @Test
            @DisplayName("nickname은 변경하지 않는다.")
            void it_does_not_change_nickname() {

                user2.change(profileRequestDto);

                assertThat(user2.getNickname()).isNotNull();
            }
        }

        @Nested
        @DisplayName("만약 nickname이 공백이라면")
        class with_nickname_white_space{

            ProfileRequestDto profileRequestDto = ProfileRequestDto.builder()
                    .nickname(" ")
                    .introduce("하이")
                    .build();

            @Test
            @DisplayName("nickname은 변경하지 않는다.")
            void it_does_not_change_nickname() {

                user2.change(profileRequestDto);

                assertThat(user2.getNickname()).isNotEqualTo(" ");
            }
        }

        @Nested
        @DisplayName("만약 nickname이 아무 값이 없다면")
        class with_nickname_no_value{

            ProfileRequestDto profileRequestDto = ProfileRequestDto.builder()
                    .nickname("")
                    .introduce("하이")
                    .build();

            @Test
            @DisplayName("nickname은 변경하지 않는다.")
            void it_does_not_change_nickname() {

                user2.change(profileRequestDto);

                assertThat(user2.getNickname()).isNotBlank();
            }
        }

        @Nested
        @DisplayName("만약 introduce가 null이라면")
        class with_introduce_null{

            ProfileRequestDto profileRequestDto = ProfileRequestDto.builder()
                    .nickname("잡스")
                    .build();

            @Test
            @DisplayName("introduce를 변경하지 않는다.")
            void it_does_not_change_introduce() {

                user2.change(profileRequestDto);

                assertThat(user2.getIntroduce()).isNotNull();
            }
        }
    }

    @Nested
    @DisplayName("changeProfileImgUrl 메서드는")
    class change_profile_image_url{

        @Nested
        @DisplayName("profileImgUrl을 가지고")
        class with_profile_img_url{

            String imageUrl = "url";

            @Test
            @DisplayName("유저 프로필 이미지를 변경한다.")
            void it_changes_user_profile_image() {

                user.changeProfileImgUrl(imageUrl);

                assertThat(user.getProfileImgUrl()).isEqualTo(imageUrl);
            }
        }

        @Nested
        @DisplayName("만약 profileImgUrl이 null 이라면")
        class with_profile_img_url_null{

            String imageUrl = null;

            @Test
            @DisplayName("유저 프로필 이미지를 변경하지 않는다.")
            void it_does_not_change_profile_image() {

                user.changeProfileImgUrl(imageUrl);

                assertThat(user.getProfileImgUrl()).isNotNull();
            }
        }

        @Nested
        @DisplayName("만약 profileImgUrl이 공백이라면")
        class with_profile_img_url_white_space{

            String imageUrl = " ";

            @Test
            @DisplayName("유저 프로필 이미지를 변경하지 않는다.")
            void it_does_not_change_profile_image() {

                user.changeProfileImgUrl(imageUrl);

                assertThat(user.getProfileImgUrl()).isNotEqualTo(" ");
            }
        }

        @Nested
        @DisplayName("만약 profileImgUrl이 값이 없다면")
        class with_profile_img_url_no_value{

            String imageUrl = "";

            @Test
            @DisplayName("유저 프로필 이미지를 변경하지 않는다.")
            void it_does_not_change_profile_image() {

                user.changeProfileImgUrl(imageUrl);

                assertThat(user.getProfileImgUrl()).isNotBlank();
            }
        }
    }

    @Nested
    @DisplayName("changeNotification 메서드는")
    class change_notification{

        User user2 = User.builder()
                .oauthId("kakaoId")
                .email("test@test.com")
                .provider(Provider.KAKAO)
                .nickname("심플")
                .introduce("소개")
                .alarmStatus("N")
                .build();

        @Nested
        @DisplayName("alarmStatus를 가지고")
        class with_alarm_status{

            @Test
            @DisplayName("알람 수신 여부를 변경한다.")
            void it_changes_alarm_status() {

                user2.changeNotification("Y");

                assertThat(user2.getAlarmStatus()).isEqualTo("Y");
            }
        }

        @Nested
        @DisplayName("만약 alarmStatus 값이 null 이라면")
        class with_alarm_status_null{

            String alarmStatus = null;

            @Test
            @DisplayName("알람 수신 여부를 변경하지 않는다.")
            void it_does_not_change_alarm_status() {

                user2.changeNotification(alarmStatus);

                assertThat(user2.getAlarmStatus()).isNotNull();
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메서드는")
    class delete_user{

        @Test
        @DisplayName("유저의 status를 inactive로 변경시킨다.")
        void it_changes_user_status_to_inactive() {

            user.deleteUser();

            assertThat(user.getStatus()).isEqualTo(BaseEntity.Status.INACTIVE);
        }
    }
}
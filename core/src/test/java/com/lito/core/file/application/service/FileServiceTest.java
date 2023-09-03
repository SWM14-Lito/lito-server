package com.lito.core.file.application.service;

import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.security.AuthUser;
import com.lito.core.file.application.port.out.FilePort;
import com.lito.core.user.adapter.out.persistence.UserRepository;
import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.lito.core.common.exception.user.UserErrorCode.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("FileService")
class FileServiceTest {

    @Autowired
    FileService fileService;

    @Autowired
    UserRepository userRepository;

    @MockBean
    FilePort filePort;

    User user = User.builder()
            .oauthId("kakaoId")
            .email("test@test.com")
            .provider(Provider.KAKAO)
            .build();

    @BeforeEach
    void setUp(){
        userRepository.save(user);
    }

    @Nested
    @DisplayName("upload 메서드는")
    class upload{

        MockMultipartFile file = new MockMultipartFile("file", "image.png","image/png","file".getBytes());

        @Nested
        @DisplayName("authUser, multipartFile을 가지고")
        class with_auth_user_multipartfile{

            AuthUser authUser = AuthUser.of(user);

            @Test
            @DisplayName("user의 profileImgUrl을 변경한다.")
            void it_changes_user_profile_img_url() throws Exception{
                given(filePort.upload(file)).willReturn(file.getName());

                fileService.upload(authUser, file);

                assertThat(user.getProfileImgUrl()).isEqualTo(file.getName());
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 유저라면")
        class with_user_not_found{

            User notFoundUser = User.builder()
                    .id(999L)
                    .oauthId("appleId")
                    .email("apple@icloud.com")
                    .provider(Provider.APPLE)
                    .build();
            AuthUser notFoundAuthUser = AuthUser.of(notFoundUser);

            @Test
            @DisplayName("USER_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_user_not_found() throws Exception{

                assertThatThrownBy(() -> fileService.upload(notFoundAuthUser, file))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(USER_NOT_FOUND.getMessage());
            }
        }
    }
}
package com.lito.core.problem.application.service;

import com.lito.core.common.entity.BaseEntity;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.security.AuthUser;
import com.lito.core.problem.adapter.out.persistence.FavoriteRepository;
import com.lito.core.problem.adapter.out.persistence.ProblemRepository;
import com.lito.core.problem.adapter.out.persistence.ProblemUserRepository;
import com.lito.core.problem.application.port.in.request.ProblemSubmitRequestDto;
import com.lito.core.problem.application.port.in.response.ProblemSubmitResponseDto;
import com.lito.core.problem.application.service.ProblemCommandService;
import com.lito.core.problem.domain.*;
import com.lito.core.user.adapter.out.persistence.UserRepository;
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

import static com.lito.core.common.exception.problem.ProblemErrorCode.PROBLEM_INVALID;
import static com.lito.core.common.exception.problem.ProblemErrorCode.PROBLEM_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("ProblemCommandService")
class ProblemCommandServiceTest {

    @Autowired
    ProblemCommandService problemCommandService;

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProblemUserRepository problemUserRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    User user = User.builder()
            .oauthId("kakaoId")
            .email("test@test.com")
            .provider(Provider.KAKAO)
            .build();
    AuthUser authUser = AuthUser.of(user);

    Problem problem = Problem.builder()
            .subject(Subject.builder()
                    .subjectName("운영체제")
                    .build())
            .subjectCategory(SubjectCategory.builder()
                    .subjectCategoryName("프로세스관리")
                    .build())
            .question("문맥 전환이 무엇인가?")
            .answer("CPU가 이전 상태의 프로세스를 PCB에 보관하고, 또 다른 프로세스를 PCB에서 읽어 레지스터에 적재하는 과정")
            .keyword("PCB")
            .build();

    Problem problem2 = Problem.builder()
            .subject(Subject.builder()
                    .subjectName("운영체제")
                    .build())
            .subjectCategory(SubjectCategory.builder()
                    .subjectCategoryName("프로세스관리")
                    .build())
            .question("프로세스란 무엇인가?")
            .answer("프로세스는 실행 중인 프로그램으로 디스크로부터 메모리에 적재되어 CPU의 할당을 받을 수 있는 것을 말한다")
            .keyword("CPU")
            .build();

    ProblemUser problemUser = ProblemUser.createProblemUser(problem, user);

    Long id;
    Long id2;

    @BeforeEach
    void setUp(){
        userRepository.save(user);
        id = problemRepository.save(problem).getId();
        id2 = problemRepository.save(problem2).getId();

        problemUserRepository.save(problemUser);
    }

    @Nested
    @DisplayName("createProblemUser 메서드는")
    class createProblemUser{

        @Nested
        @DisplayName("authUser, id를 가지고 만약 problemUser가 존재하면 조회되고")
        class with_auth_user_id_existed_problem_user{

            @Test
            @DisplayName("problemUser가 확인된다.")
            void it_checks_problem_user() throws Exception{

                assertThatCode(() -> problemCommandService.createProblemUser(authUser, id))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("authUser,id를 가지고 만약 problemUser가 존재하지 않는다면")
        class with_auth_user_id_not_existed_problem_user{

            @Test
            @DisplayName("problemUser를 저장한다.")
            void it_saves_problem_user() {

                assertThatCode(() -> problemCommandService.createProblemUser(authUser, id2))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 문제라면")
        class with_problem_not_found{

            Long id = 999L;

            @Test
            @DisplayName("PROBLEM_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_problem_not_found() {

                assertThatThrownBy(() -> problemCommandService.createProblemUser(authUser, id))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(PROBLEM_NOT_FOUND.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("submit 메서드는")
    class submit{

        @Nested
        @DisplayName("authUser, id, problemSubmitRequestDto를 가지고 정답이면")
        class with_auth_user_id_problem_submit_request_dto_pass{

            ProblemSubmitRequestDto requestDto = ProblemSubmitRequestDto.builder()
                    .keyword("PCB")
                    .build();

            @Test
            @DisplayName("problem submit response dto의 solved 값으로 true를 리턴한다.")
            void it_returns_problem_submit_response_dto_pass() {

                ProblemSubmitResponseDto responseDto = problemCommandService.submit(authUser, id, requestDto);

                assertThat(responseDto.isSolved()).isTrue();
            }
        }

        @Nested
        @DisplayName("authUser, id, problemSubmitRequestDto를 가지고 오답이면")
        class with_auth_user_id_problem_submit_request_dto_fail{

            ProblemSubmitRequestDto requestDto = ProblemSubmitRequestDto.builder()
                    .keyword("CPU")
                    .build();

            @Test
            @DisplayName("problem submit response dto의 solved 값으로 false를 리턴하고, unsolvedCnt를 올린다.")
            void it_returns_problem_submit_response_dto_fail_add_unsolved_cnt() {

                ProblemSubmitResponseDto responseDto = problemCommandService.submit(authUser, id, requestDto);

                assertThat(responseDto.isSolved()).isFalse();
                assertThat(problemUser.getUnsolvedCnt()).isEqualTo(1);
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 문제라면")
        class with_problem_not_found{

            Long id = 999L;
            ProblemSubmitRequestDto requestDto = ProblemSubmitRequestDto.builder()
                    .keyword("keyword")
                    .build();

            @Test
            @DisplayName("PROBLEM_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_problem_not_found() {


                assertThatThrownBy(() -> problemCommandService.submit(authUser, id, requestDto))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(PROBLEM_NOT_FOUND.getMessage());
            }
        }

        @Nested
        @DisplayName("만약 올바르지 못한 접근이라면")
        class with_problem_invalid{

            User user2 = User.builder()
                    .oauthId("appleId")
                    .email("test@icloud.com")
                    .provider(Provider.APPLE)
                    .build();
            AuthUser authUser2 = AuthUser.of(user2);

            @BeforeEach
            void setUp(){
                userRepository.save(user2);
            }

            ProblemSubmitRequestDto requestDto = ProblemSubmitRequestDto.builder()
                    .keyword("keyword")
                    .build();

            @Test
            @DisplayName("PROBLEM_INVALID 예외를 발생시킨다.")
            void it_throws_problem_invalid() {

                assertThatThrownBy(() -> problemCommandService.submit(authUser2, id, requestDto))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(PROBLEM_INVALID.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("updateFavorite 메서드는")
    class update_favorite{

        @Nested
        @DisplayName("만약 favorite이 존재하고 favorite의 status가 ACTIVE라면, authUser,id를 가지고")
        class with_auth_user_id_favorite_present_active{

            Favorite favorite = Favorite.createFavorite(user, problem);
            @BeforeEach
            void setUp(){
                favoriteRepository.save(favorite);
            }

            @Test
            @DisplayName("status를 INACTIVE로 변경한다.")
            void it_changes_favorite_status_to_inactive() {

                problemCommandService.updateFavorite(authUser, id);

                assertThat(favorite.getStatus()).isEqualTo(BaseEntity.Status.INACTIVE);
            }
        }

        @Nested
        @DisplayName("만약 favorite이 존재하고 favorite의 status가 INACTIVE라면, authUser, id를 가지고")
        class with_auth_user_id_favorite_present_inactive{

            Favorite favorite2 = Favorite.createFavorite(user, problem2);

            @BeforeEach
            void setUp(){
                favoriteRepository.save(favorite2);
                favorite2.changeStatus(BaseEntity.Status.INACTIVE);
            }

            @Test
            @DisplayName("status를 ACTIVE로 변경한다.")
            void it_changes_favorite_status_to_active() throws Exception{
                problemCommandService.updateFavorite(authUser, id2);

                assertThat(favorite2.getStatus()).isEqualTo(BaseEntity.Status.ACTIVE);
            }
        }

        @Nested
        @DisplayName("만약 favorite이 존재하지 않으면 authUser, id를 가지고")
        class with_auth_user_id_favorite_not_present{

            User user2 = User.builder()
                    .oauthId("appleId")
                    .email("test@icloud.com")
                    .provider(Provider.APPLE)
                    .build();
            AuthUser authUser2 = AuthUser.of(user2);

            @BeforeEach
            void setUp(){
                userRepository.save(user2);
            }

            @Test
            @DisplayName("favorite를 저장한다.")
            void it_saves_favorite() {

                assertThatCode(() -> problemCommandService.updateFavorite(authUser2,id))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 문제라면")
        class with_problem_not_found{

            Long id = 999L;

            @Test
            @DisplayName("PROBLEM_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_problem_not_found() throws Exception{

                assertThatThrownBy(() -> problemCommandService.updateFavorite(authUser, id))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(PROBLEM_NOT_FOUND.getMessage());
            }
        }
    }
}
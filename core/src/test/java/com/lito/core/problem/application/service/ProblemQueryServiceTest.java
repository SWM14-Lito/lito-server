package com.lito.core.problem.application.service;

import com.lito.core.common.entity.BaseEntity;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.security.AuthUser;
import com.lito.core.problem.adapter.out.persistence.FavoriteRepository;
import com.lito.core.problem.adapter.out.persistence.ProblemRepository;
import com.lito.core.problem.adapter.out.persistence.ProblemUserRepository;
import com.lito.core.problem.adapter.out.persistence.RecommendUserRepository;
import com.lito.core.problem.application.port.in.response.ProblemResponseDto;
import com.lito.core.problem.application.port.in.response.ProblemHomeResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemReviewPageQResponseDto;
import com.lito.core.problem.domain.*;
import com.lito.core.problem.domain.enums.ProblemStatus;
import com.lito.core.user.adapter.out.persistence.UserRepository;
import com.lito.core.user.domain.User;
import com.lito.core.user.domain.enums.Provider;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lito.core.common.exception.problem.ProblemErrorCode.PROBLEM_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("ProblemQueryService")
class ProblemQueryServiceTest {

    @Autowired
    ProblemQueryService problemQueryService;

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProblemUserRepository problemUserRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    RecommendUserRepository recommendUserRepository;

    User user = User.builder()
            .oauthId("kakaoId")
            .email("test@test.com")
            .provider(Provider.KAKAO)
            .build();
    AuthUser authUser = AuthUser.of(user);

    User user2 = User.builder()
            .oauthId("appleId")
            .email("test@apple.com")
            .provider(Provider.APPLE)
            .build();
    AuthUser authUser2 = AuthUser.of(user2);

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

    ProblemUser problemUser = ProblemUser.createProblemUser(problem, user);
    RecommendUser recommendUser;

    Long id;
    Long userId;
    Long userId2;

    @BeforeEach
    void setUp(){
        userId = userRepository.save(user).getId();
        userId2 = userRepository.save(user2).getId();
        id = problemRepository.save(problem).getId();

        problemUserRepository.save(problemUser);
        recommendUser = RecommendUser.createRecommendUser(userId, id);
        recommendUserRepository.save(recommendUser);
    }

    @Nested
    @DisplayName("find 메서드는")
    class find{

        @Nested
        @DisplayName("authUser,id를 가지고")
        class with_auth_user_id{

            @Test
            @DisplayName("problemResponseDto를 리턴한다.")
            void it_returns_problem_response_dto() {

                ProblemResponseDto responseDto = problemQueryService.find(authUser, id);

                assertAll(
                        () -> assertThat(responseDto.getProblemId()).isEqualTo(id),
                        () -> assertThat(responseDto.getProblemQuestion()).isEqualTo(problem.getQuestion()),
                        () -> assertThat(responseDto.getProblemAnswer()).isEqualTo(problem.getAnswer()),
                        () -> assertThat(responseDto.getProblemKeyword()).isEqualTo(problem.getKeyword()),
                        () -> assertThat(responseDto.getProblemStatus()).isEqualTo(problemUser.getProblemStatus().getName()),
                        () -> assertThat(responseDto.isFavorite()).isFalse()
                );
            }
        }

        @Nested
        @DisplayName("만약 favorite이 존재하고 status가 ACTIVE 라면")
        class with_favorite_present_status_active{

            Favorite favorite = Favorite.createFavorite(user, problem);
            @BeforeEach
            void setUp(){
                favoriteRepository.save(favorite);
            }

            @Test
            @DisplayName("problemResponseDto의 favorite 값을 true로 리턴한다.")
            void it_returns_favorite_true() throws Exception{

                ProblemResponseDto responseDto = problemQueryService.find(authUser, id);

                assertThat(responseDto.isFavorite()).isTrue();
            }
        }

        @Nested
        @DisplayName("만약 favorite이 존재하고 status가 INACTIVE 라면")
        class with_favorite_present_status_inactive{

            Favorite favorite = Favorite.createFavorite(user, problem);
            @BeforeEach
            void setUp(){
                favoriteRepository.save(favorite);
                favorite.changeStatus(BaseEntity.Status.INACTIVE);
            }

            @Test
            @DisplayName("problemResponseDto의 favorite 값을 false 리턴한다.")
            void it_returns_favorite_false() throws Exception{

                ProblemResponseDto responseDto = problemQueryService.find(authUser, id);

                assertThat(responseDto.isFavorite()).isFalse();
            }
        }

        @Nested
        @DisplayName("만약 favorite이 존재하지 않는다면")
        class with_favorite_not_present{

            @Test
            @DisplayName("problemResponseDto의 favorite 값을 false 리턴한다.")
            void it_returns_favorite_true() throws Exception{

                ProblemResponseDto responseDto = problemQueryService.find(authUser, id);

                assertThat(responseDto.isFavorite()).isFalse();
            }
        }


        @Nested
        @DisplayName("만약 존재하지 않는 문제라면")
        class with_problem_not_found{

            Long id = 999L;

            @Test
            @DisplayName("PROBLEM_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_problem_not_found() {

                assertThatThrownBy(() -> problemQueryService.find(authUser, id))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(PROBLEM_NOT_FOUND.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("findProblemPage 메서드는")
    class find_problem_page{

        @Nested
        @DisplayName("authUser, subjectId, problemStatus, query, pageable을 가지고")
        class with_auth_user_subject_id_problem_status_query_pageable{

            Pageable pageable = PageRequest.of(0, 10);

            @Test
            @DisplayName("problemPageQuerydslResponseDto를 리턴한다.")
            void it_returns_problem_page_querydsl_response_dto() {

                Page<ProblemPageQueryDslResponseDto> pageResponseDto = problemQueryService.findProblemPage(authUser,null,null,
                        null,pageable);

                ProblemPageQueryDslResponseDto responseDto = pageResponseDto.getContent().get(0);

                assertAll(
                        () -> assertThat(pageResponseDto.getTotalElements()).isEqualTo(1),
                        () -> assertThat(responseDto.getProblemId()).isEqualTo(id),
                        () -> assertThat(responseDto.getSubjectName()).isEqualTo(problem.getSubject().getSubjectName()),
                        () -> assertThat(responseDto.getQuestion()).isEqualTo(problem.getQuestion()),
                        () -> assertThat(responseDto.getProblemStatus()).isEqualTo(problemUser.getProblemStatus()),
                        () -> assertThat(responseDto.isFavorite()).isFalse()
                );

            }
        }
    }

    @Nested
    @DisplayName("findProblemPageWithProcess 메서드는")
    class find_problem_page_with_process{

        @Nested
        @DisplayName("authUser, pageable을 가지고")
        class with_auth_user_pageable{

            Pageable pageable = PageRequest.of(0, 10);

            @Test
            @DisplayName("problem page with process q response dto를 리턴한다.")
            void it_returns_problem_page_with_process_q_response_dto() {

                Page<ProblemPageWithProcessQResponseDto> pageResponseDto = problemQueryService.findProblemPageWithProcess(authUser, pageable);

                ProblemPageWithProcessQResponseDto responseDto = pageResponseDto.getContent().get(0);

                assertAll(
                        () -> assertThat(pageResponseDto.getTotalElements()).isEqualTo(1),
                        () -> assertThat(responseDto.getProblemId()).isEqualTo(id),
                        () -> assertThat(responseDto.getSubjectName()).isEqualTo(problem.getSubject().getSubjectName()),
                        () -> assertThat(responseDto.getQuestion()).isEqualTo(problem.getQuestion()),
                        () -> assertThat(responseDto.isFavorite()).isFalse()
                );
            }
        }
    }

    @Nested
    @DisplayName("findProblemPageWithFavorite 메서드는")
    class find_problem_page_with_favorite{

        @Nested
        @DisplayName("authUser, subjectId, problemStatus, pageable을 가지고")
        class with_auth_user_subject_id_problem_status_pageable{

            Pageable pageable = PageRequest.of(0, 10);

            Long favoriteId;
            @BeforeEach
            void setUp(){
                favoriteId = favoriteRepository.save(Favorite.createFavorite(user, problem)).getId();
            }

            @Test
            @DisplayName("problem page with favorite q response dto")
            void it_returns_problem_page_with_favorite_q_response_dto() {

                Page<ProblemPageWithFavoriteQResponseDto> pageResponseDto = problemQueryService.findProblemPageWithFavorite(authUser, null, null, pageable);

                ProblemPageWithFavoriteQResponseDto responseDto = pageResponseDto.getContent().get(0);

                assertAll(
                        () -> assertThat(pageResponseDto.getTotalElements()).isEqualTo(1),
                        () -> assertThat(responseDto.getFavoriteId()).isEqualTo(favoriteId),
                        () -> assertThat(responseDto.getProblemId()).isEqualTo(id),
                        () -> assertThat(responseDto.getSubjectName()).isEqualTo(problem.getSubject().getSubjectName()),
                        () -> assertThat(responseDto.getQuestion()).isEqualTo(problem.getQuestion()),
                        () -> assertThat(responseDto.getProblemStatus()).isEqualTo(problemUser.getProblemStatus())
                );
            }
        }
    }

    @Nested
    @DisplayName("findHome 메서드는")
    class find_home{

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

        ProblemUser problemUser2 = ProblemUser.builder()
                .user(user)
                .problem(problem2)
                .problemStatus(ProblemStatus.COMPLETE)
                .unsolvedCnt(1)
                .build();
        @Nested
        @DisplayName("authUser를 가지고")
        class with_auth_user{

            @BeforeEach
            void setUp(){
                problemRepository.save(problem2);
                problemUserRepository.save(problemUser2);
            }

            @Test
            @DisplayName("problem user response dto를 리턴한다.")
            void it_returns_problem_home_response_dto() {

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser);

                assertAll(
                        () -> assertThat(responseDto.getUserId()).isEqualTo(userId),
                        () -> assertThat(responseDto.getProfileImgUrl()).isEqualTo(user.getProfileImgUrl()),
                        () -> assertThat(responseDto.getNickname()).isEqualTo(user.getNickname()),
                        () -> assertThat(responseDto.getCompleteProblemCntInToday()).isEqualTo(1),
                        () -> assertThat(responseDto.getProcessProblemResponseDto().getProblemId()).isEqualTo(id),
                        () -> assertThat(responseDto.getProcessProblemResponseDto().getSubjectName()).isEqualTo(problem.getSubject().getSubjectName()),
                        () -> assertThat(responseDto.getProcessProblemResponseDto().getQuestion()).isEqualTo(problem.getQuestion()),
                        () -> assertThat(responseDto.getProcessProblemResponseDto().isFavorite()).isFalse(),
                        () -> assertThat(responseDto.getRecommendUserResponseDtos()).hasSize(1)
                );
            }
        }

        @Nested
        @DisplayName("만약 favorite이 존재하고 status가 ACTIVE 라면")
        class with_favorite_present_status_active{

            Favorite favorite = Favorite.createFavorite(user, problem);
            @BeforeEach
            void setUp(){
                favoriteRepository.save(favorite);
            }

            @Test
            @DisplayName("problemHomeResponseDto의 favorite 값을 true로 리턴한다.")
            void it_returns_favorite_true() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser);

                assertThat(responseDto.getProcessProblemResponseDto().isFavorite()).isTrue();
            }
        }

        @Nested
        @DisplayName("만약 favorite이 존재하고 status가 INACTIVE 라면")
        class with_favorite_present_status_inactive{

            Favorite favorite = Favorite.createFavorite(user, problem);
            @BeforeEach
            void setUp(){
                favoriteRepository.save(favorite);
                favorite.changeStatus(BaseEntity.Status.INACTIVE);
            }

            @Test
            @DisplayName("problemHomeResponseDto의 favorite 값을 false로 리턴한다.")
            void it_returns_favorite_false() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser);

                assertThat(responseDto.getProcessProblemResponseDto().isFavorite()).isFalse();
            }
        }

        @Nested
        @DisplayName("만약 favorite이 존재하지 않는다면")
        class with_favorite_not_present{

            @Test
            @DisplayName("problemHomeResponseDto의 favorite 값을 false로 리턴한다.")
            void it_returns_favorite_true() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser);

                assertThat(responseDto.getProcessProblemResponseDto().isFavorite()).isFalse();
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 문제라면")
        class with_problem_not_found{

            @BeforeEach
            void setUp(){
                problem.changeStatus(BaseEntity.Status.INACTIVE);
            }

            @Test
            @DisplayName("PROBLEM_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_problem_not_found() {

                assertThatThrownBy(() -> problemQueryService.findHome(authUser))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(PROBLEM_NOT_FOUND.getMessage());
            }
        }

        @Nested
        @DisplayName("만약 풀던 문제가 없다면")
        class with_problemuser_not_found{

            @Test
            @DisplayName("ProblemHomeResponseDto의 ProcessProblemResponseDto값들은 전부 null을 리턴한다.")
            void it_returns_problem_home_response_dto_with_all_value_null() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser2);

                assertThat(responseDto.getProcessProblemResponseDto())
                        .extracting("problemId","subjectName","question","favorite")
                        .contains(null,null,null,null);
            }
        }

        @Nested
        @DisplayName("만약 추천 문제가 존재하지 않는 문제라면")
        class with_recommend_problem_not_found{

            @BeforeEach
            void setUp(){
                recommendUserRepository.save(RecommendUser.createRecommendUser(userId2, 999L));
            }

            @Test
            @DisplayName("PROBLEM_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_problem_not_found() throws Exception{

                assertThatThrownBy(() -> problemQueryService.findHome(authUser2))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(PROBLEM_NOT_FOUND.getMessage());
            }
        }

        @Nested
        @DisplayName("만약 추천 문제가 풀던 문제가 아니라면")
        class with_recommend_problem_not_process_problem{


            @BeforeEach
            void setUp(){
                recommendUserRepository.save(RecommendUser.createRecommendUser(userId2, id));
            }

            @Test
            @DisplayName("ProblemStatus.NOT_SEEN을 리턴한다.")
            void it_returns_problem_status_not_seen() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser2);

                assertThat(responseDto.getRecommendUserResponseDtos().get(0).getProblemStatus())
                        .isEqualTo(ProblemStatus.NOT_SEEN.getName());
            }
        }

        @Nested
        @DisplayName("만약 추천 문제의 favorite이 존재하고 status가 ACTIVE 라면")
        class with_recommend_problem_favorite_present_status_active{

            Favorite favorite = Favorite.createFavorite(user, problem);
            @BeforeEach
            void setUp(){
                favoriteRepository.save(favorite);
            }

            @Test
            @DisplayName("problemHomeResponseDto의 favorite 값을 true로 리턴한다.")
            void it_returns_favorite_true() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser);

                assertThat(responseDto.getRecommendUserResponseDtos().get(0).isFavorite())
                        .isTrue();
            }
        }

        @Nested
        @DisplayName("만약 추천 문제의 favorite이 존재하고 status가 INACTIVE 라면")
        class with_recommend_user_favorite_present_status_inactive{

            Favorite favorite = Favorite.createFavorite(user, problem);
            @BeforeEach
            void setUp(){
                favoriteRepository.save(favorite);
                favorite.changeStatus(BaseEntity.Status.INACTIVE);
            }

            @Test
            @DisplayName("problemHomeResponseDto의 favorite 값을 false로 리턴한다.")
            void it_returns_favorite_false() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser);

                assertThat(responseDto.getRecommendUserResponseDtos().get(0).isFavorite())
                        .isFalse();
            }
        }

        @Nested
        @DisplayName("만약 추천 문제의 favorite이 존재하지 않는 다면")
        class with_recommend_user_favorite_not_present{

            @Test
            @DisplayName("problemHomeResponseDto의 favorite 값을 false로 리턴한다.")
            void it_returns_favorite_true() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser);

                assertThat(responseDto.getRecommendUserResponseDtos().get(0).isFavorite())
                        .isFalse();
            }
        }

        @Nested
        @DisplayName("만약 추천 문제가 존재하지 않는다면")
        class with_recommend_user_not_found{

            @BeforeEach
            void setUp(){
                problemRepository.save(problem2);
            }

            @Test
            @DisplayName("랜덤으로 문제를 리턴한다.")
            void it_returns_random_problem() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser2);

                assertThat(responseDto.getRecommendUserResponseDtos().size()).isEqualTo(2);

            }
        }

        @Nested
        @DisplayName("만약 추천 문제가 존재하지 않고, 풀던 문제가 포함되어 있다면")
        class with_recommend_user_not_found_process_problem{

            @BeforeEach
            void setUp(){
                ProblemUser problemUser = ProblemUser.createProblemUser(problem, user2);
                problemUserRepository.save(problemUser);

            }
            @Test
            @DisplayName("랜덤 문제는 풀던 문제 상태로 리턴한다.")
            void it_returns_random_problem_in_progress_status() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser2);

                assertThat(responseDto.getRecommendUserResponseDtos().get(0).getProblemStatus())
                        .isEqualTo(ProblemStatus.PROCESS.getName());

            }
        }

        @Nested
        @DisplayName("만약 추천 문제가 존재하지 않고, 찜한 문제가 포함되어 있다면")
        class with_recommend_user_not_found_favorite{

            Favorite favorite = Favorite.createFavorite(user2, problem);
            @BeforeEach
            void setUp(){
                favoriteRepository.save(favorite);
            }

            @Test
            @DisplayName("랜덤 문제는 찜한 상태로 리턴한다.")
            void it_returns_random_problem_in_favorite() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser2);

                assertThat(responseDto.getRecommendUserResponseDtos().get(0).isFavorite())
                        .isTrue();
            }
        }

        @Nested
        @DisplayName("만약 추천 문제가 존재하지 않고, 찜한 문제가 포함되어 있지 않다면")
        class with_recommend_user_not_found_not_favorite{

            @BeforeEach
            void setUp(){
                problemRepository.save(problem2);
                Favorite favorite = Favorite.createFavorite(user2, problem2);
                favoriteRepository.save(favorite);
                favorite.changeStatus(BaseEntity.Status.INACTIVE);
            }

            @Test
            @DisplayName("랜덤 문제는 찜한 상태로 리턴한다.")
            void it_returns_random_problem_in_favorite() throws Exception{

                ProblemHomeResponseDto responseDto = problemQueryService.findHome(authUser2);

                assertThat(responseDto.getRecommendUserResponseDtos().get(0).isFavorite())
                        .isFalse();
            }
        }
    }

    @Nested
    @DisplayName("findAllProblemUser 메서드는")
    class find_all_problemuser{

        @Test
        @DisplayName("모든 ProblemUser를 리턴한다.")
        void it_returns_all_problemuser() throws Exception{

            List<ProblemUser> problemUsers = problemQueryService.findAllProblemUser();

            assertThat(problemUsers.size()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("findAllProblem 메서드는")
    class find_all_problem{

        @Test
        @DisplayName("모든 문제를 리턴한다.")
        void it_returns_all_problem() throws Exception{

            List<Problem> problems = problemQueryService.findAllProblem();

            assertThat(problems.size()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("findProblemReviewPage 메서드는")
    class find_problem_review_page{

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

        ProblemUser problemUser2 = ProblemUser.builder()
                .user(user)
                .problem(problem2)
                .problemStatus(ProblemStatus.COMPLETE)
                .unsolvedCnt(1)
                .build();

        @Nested
        @DisplayName("authUser와 pageable을 가지고")
        class with_auth_user_pageable{

            Pageable pageable = PageRequest.of(0, 10);

            @BeforeEach
            void setUp(){
                problemRepository.save(problem2);
                problemUserRepository.save(problemUser2);
            }

            @Test
            @DisplayName("problemReviewPageQResponseDto를 리턴한다.")
            void it_returns_problem_review_page_q_response_dto() throws Exception{
                Page<ProblemReviewPageQResponseDto> pageResponseDtos = problemQueryService.findProblemReviewPage(authUser, pageable);

                ProblemReviewPageQResponseDto responseDto = pageResponseDtos.getContent().get(0);

                assertThat(responseDto.getUnsolvedCnt()).isEqualTo(1);
            }
        }

        @Nested
        @DisplayName("만약 한 번 이상이라도 틀린 문제가 존재하지 않을 경우")
        class with_not_found_problem_with_unsolved_problem{

            Pageable pageable = PageRequest.of(0, 10);

            @Test
            @DisplayName("빈 리스트를 리턴한다.")
            void it_returns_empty_list() throws Exception{
                Page<ProblemReviewPageQResponseDto> pageResponseDtos = problemQueryService.findProblemReviewPage(authUser, pageable);

                assertThat(pageResponseDtos.getContent().size()).isEqualTo(0);
            }
        }
    }
}
package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.adapter.out.persistence.*;
import com.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("ProblemQueryAdapter")
class ProblemQueryAdapterTest {

    @Autowired
    ProblemQueryAdapter problemQueryAdapter;

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
            .faqs(List.of(Faq.createFaq("PCB란?", "PCB 는 특정프로세스에 대한 중요한 정보를 저장하고 있는 운영체제의 자료구조이다")))
            .build();

    ProblemUser problemUser = ProblemUser.createProblemUser(problem, user);
    RecommendUser recommendUser;

    Long id;
    Long userId;

    @BeforeEach
    void setUp(){
        userId = userRepository.save(user).getId();
        id = problemRepository.save(problem).getId();
        problemUserRepository.save(problemUser);

        recommendUser = RecommendUser.createRecommendUser(userId, id);
        recommendUserRepository.save(recommendUser);

    }

    @Nested
    @DisplayName("findProblemWithFaqById 메서드는")
    class find_problem_with_faq_by_id{

        @Nested
        @DisplayName("id를 가지고")
        class with_id{

            @Test
            @DisplayName("faqs가 포함된 problem을 리턴한다.")
            void it_returns_problem_with_faqs() {

                Problem problem = problemQueryAdapter.findProblemWithFaqById(id).get();

                assertThat(problem.getId()).isEqualTo(id);
                assertThat(problem.getFaqs()).hasSize(1);
            }
        }
    }

    @Nested
    @DisplayName("findProblemById 메서드는")
    class find_problem_by_id{

        @Nested
        @DisplayName("id를 가지고")
        class with_id{

            @Test
            @DisplayName("problem을 리턴한다.")
            void it_returns_problem() {

                Problem problem = problemQueryAdapter.findProblemById(id).get();

                assertThat(problem.getId()).isEqualTo(id);
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
            @DisplayName("problem page querydsl response dto를 리턴한다.")
            void it_returns_problem_page_querydsl_response_dto() {

                Page<ProblemPageQueryDslResponseDto> pageResponseDto = problemQueryAdapter.findProblemPage(userId,null,null,
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

                Page<ProblemPageWithProcessQResponseDto> pageResponseDto = problemQueryAdapter.findProblemPageWithProcess(userId, pageable);

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

                Page<ProblemPageWithFavoriteQResponseDto> pageResponseDto = problemQueryAdapter.findProblemPageWithFavorite(userId, null, null, pageable);

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
    @DisplayName("existsByUserAndProblem 메서드는")
    class exists_by_user_and_problem{

        @Nested
        @DisplayName("user,problem을 가지고")
        class with_user_problem{

            @Test
            @DisplayName("favorite 엔티티의 존재 여부로 찜한 여부를 리턴한다.")
            void it_returns_boolean() {

                boolean result = problemQueryAdapter.existsByUserAndProblem(user, problem);

                assertThat(result).isFalse();
            }
        }
    }

    @Nested
    @DisplayName("findByUserAndProblem")
    class find_by_user_and_problem{

        @Nested
        @DisplayName("user, problem을 가지고")
        class with_user_problem{

            Long favoriteId;
            @BeforeEach
            void setUp(){
                favoriteId = favoriteRepository.save(Favorite.createFavorite(user, problem)).getId();
            }

            @Test
            @DisplayName("favorite을 리턴한다.")
            void it_returns_favorite() {

                Favorite favorite = problemQueryAdapter.findByUserAndProblem(user, problem).get();

                assertThat(favorite.getId()).isEqualTo(favoriteId);
            }
        }
    }

    @Nested
    @DisplayName("findFirstProblemUser")
    class find_first_problem_user{

        @Nested
        @DisplayName("user를 가지고")
        class with_user{

            Long id2;
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

            ProblemUser problemUser2 = ProblemUser.createProblemUser(problem2, user);


            @BeforeEach
            void setUp(){
                id2 = problemRepository.save(problem2).getId();
                problemUserRepository.save(problemUser2);
            }

            @Test
            @DisplayName("problemUser를 리턴한다.")
            void it_returns_problem_user() {

                ProblemUser problemUser2 = problemQueryAdapter.findFirstProblemUser(user).get();

                assertThat(problemUser2.getProblem().getId()).isEqualTo(id2);
            }
        }
    }

    @Nested
    @DisplayName("findByProblemAndUser 메서드는")
    class find_by_problem_and_user{

        @Nested
        @DisplayName("problem, user를 가지고")
        class with_problem_user{

            @Test
            @DisplayName("problemUser를 리턴한다.")
            void it_returns_problem_user() {

                ProblemUser foundProblemUser = problemUserRepository.findByProblemAndUser(problem, user).get();

                assertThat(foundProblemUser.getProblem().getId()).isEqualTo(id);
            }
        }
    }

    @Nested
    @DisplayName("findRecommendUsers 메서드는")
    class find_recommend_users{

        @Nested
        @DisplayName("userId를 가지고")
        class with_user_id{

            @Test
            @DisplayName("List형태의 RecommendUser를 리턴한다.")
            void it_returns_recommend_user_in_list() {

                List<RecommendUser> recommendUsers = problemQueryAdapter.findRecommendUsers(userId);

                assertThat(recommendUsers).hasSize(1);
            }
        }
    }
}
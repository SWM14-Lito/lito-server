package com.lito.core.admin.application.service;

import com.lito.core.admin.adapter.out.persistence.AdminProblemRepository;
import com.lito.core.admin.adapter.out.persistence.AdminSubjectCategoryRepository;
import com.lito.core.admin.adapter.out.persistence.AdminSubjectRepository;
import com.lito.core.admin.application.port.in.request.FaqRequestDto;
import com.lito.core.admin.application.port.in.request.ProblemRequestDto;
import com.lito.core.common.entity.BaseEntity;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.Subject;
import com.lito.core.problem.domain.SubjectCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lito.core.common.exception.admin.AdminErrorCode.*;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("AdminProblemCommandService")
class AdminProblemCommandServiceTest {

    @Autowired
    AdminProblemCommandService adminProblemCommandService;

    @Autowired
    AdminSubjectRepository adminSubjectRepository;

    @Autowired
    AdminSubjectCategoryRepository adminSubjectCategoryRepository;

    @Autowired
    AdminProblemRepository adminProblemRepository;

    @Nested
    @DisplayName("create 메서드는")
    class create {

        Long subjectId;
        Long subjectCategoryId;

        @Nested
        @DisplayName("problemRequestDto를 가지고")
        class with_problem_request_dto{

            ProblemRequestDto requestDto;

            @BeforeEach
            void setUp(){

                subjectId = adminSubjectRepository.save(Subject.builder()
                        .subjectName("운영체제")
                        .build()).getId();
                subjectCategoryId = adminSubjectCategoryRepository.save(SubjectCategory.builder()
                        .subjectCategoryName("프로세스관리")
                        .build()).getId();

                requestDto = ProblemRequestDto.builder()
                        .subjectId(subjectId)
                        .subjectCategoryId(subjectCategoryId)
                        .question("문맥 전환이 무엇인가?")
                        .answer("CPU가 이전 상태의 프로세스를 PCB에 보관하고, 또 다른 프로세스를 PCB에서 읽어 레지스터에 적재하는 과정")
                        .keyword("PCB")
                        .faqRequestDtos(List.of(FaqRequestDto.builder().
                                question("PCB란?")
                                .answer("PCB 는 특정프로세스에 대한 중요한 정보를 저장하고 있는 운영체제의 자료구조이다")
                                .build()))
                        .build();
            }


            @Test
            @DisplayName("problem을 저장한다.")
            void it_saves_problem() throws Exception{

                assertThatCode(() -> adminProblemCommandService.create(requestDto))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("만약 subject가 존재하지 않는다면")
        class with_subject_not_found{

            Long notFoundSubjectId = 999L;
            ProblemRequestDto subjectNotFoundRequestDto = ProblemRequestDto.builder()
                    .subjectId(notFoundSubjectId)
                    .build();

            @Test
            @DisplayName("SUBJECT_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_subject_not_found() throws Exception{

                assertThatThrownBy(() -> adminProblemCommandService.create(subjectNotFoundRequestDto))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(SUBJECT_NOT_FOUND.getMessage());
            }
        }

        @Nested
        @DisplayName("만약 subjectCategory가 존재하지 않는다면")
        class with_subject_category_not_found{

            Long notFoundSubjectCategoryId = 999L;
            Long subjectId;
            ProblemRequestDto subjectCategoryNotFoundRequestDto;

            @BeforeEach
            void setUp() {

                subjectId = adminSubjectRepository.save(Subject.builder()
                        .subjectName("운영체제")
                        .build()).getId();
                subjectCategoryNotFoundRequestDto = ProblemRequestDto.builder()
                        .subjectId(subjectId)
                        .subjectCategoryId(notFoundSubjectCategoryId)
                        .build();
            }

            @Test
            @DisplayName("SUBJECT_CATEGORY_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_subject_category_not_found() throws Exception{

                assertThatThrownBy(() -> adminProblemCommandService.create(subjectCategoryNotFoundRequestDto))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(SUBJECT_CATEGORY_NOT_FOUND.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class delete{

        @Nested
        @DisplayName("id를 가지고")
        class with_id{

            Long id;
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
            @BeforeEach
            void setUp(){
                id = adminProblemRepository.save(problem).getId();
            }

            @Test
            @DisplayName("problem을 삭제한다.")
            void it_deletes_problem() throws Exception{

                adminProblemCommandService.delete(id);

                assertThat(problem.getStatus()).isEqualTo(BaseEntity.Status.INACTIVE);
            }
        }

        @Nested
        @DisplayName("만약 problem이 존재하지 않는다면")
        class with_problem_not_found{

            Long notFoundProblemId = 999L;

            @Test
            @DisplayName("PROBLEM_NOT_FOUND 예외를 발생시킨다.")
            void it_throws_problem_not_found() throws Exception{

                assertThatThrownBy(() -> adminProblemCommandService.delete(notFoundProblemId))
                        .isExactlyInstanceOf(ApplicationException.class)
                        .hasMessage(PROBLEM_NOT_FOUND.getMessage());
            }
        }
    }
}
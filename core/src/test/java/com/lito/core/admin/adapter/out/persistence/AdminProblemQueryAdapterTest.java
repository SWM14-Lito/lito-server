package com.lito.core.admin.adapter.out.persistence;

import com.lito.core.problem.domain.Faq;
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

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("AdminProblemQueryAdapter")
class AdminProblemQueryAdapterTest {

    @Autowired
    AdminProblemQueryAdapter adminProblemQueryAdapter;

    @Autowired
    AdminSubjectRepository adminSubjectRepository;

    @Autowired
    AdminSubjectCategoryRepository adminSubjectCategoryRepository;

    @Autowired
    AdminProblemRepository adminProblemRepository;

    Long subjectId;
    Long subjectCategoryId;
    Long id;

    Subject subject = Subject.builder()
            .subjectName("운영체제")
            .build();

    SubjectCategory subjectCategory = SubjectCategory.builder()
            .subjectCategoryName("운영체제 개념")
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

    @BeforeEach
    void setUp(){
        subjectId = adminSubjectRepository.save(subject).getId();
        subjectCategoryId = adminSubjectCategoryRepository.save(subjectCategory).getId();
        id = adminProblemRepository.save(problem).getId();
    }

    @Nested
    @DisplayName("findSubjectById 메서드는")
    class find_subject_by_id{

        @Nested
        @DisplayName("id를 가지고")
        class with_id{

            @Test
            @DisplayName("subject를 리턴한다.")
            void it_returns_subject() throws Exception{

                Subject findedSubject = adminProblemQueryAdapter.findSubjectById(subjectId).get();

                assertThat(findedSubject.getSubjectName()).isEqualTo(subject.getSubjectName());
            }
        }
    }

    @Nested
    @DisplayName("findSubjectCategoryById 메서드는")
    class find_subject_category_by_id{

        @Nested
        @DisplayName("id를 가지고")
        class with_id{

            @Test
            @DisplayName("subjectCategory를 리턴한다.")
            void it_returns_subject_category() throws Exception{

                SubjectCategory findedSubjectCategory = adminSubjectCategoryRepository.findById(subjectCategoryId).get();

                assertThat(findedSubjectCategory.getSubjectCategoryName()).isEqualTo(subjectCategory.getSubjectCategoryName());
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
            void it_returns_problem() throws Exception{

                Problem findedProblem = adminProblemRepository.findById(id).get();

                assertThat(findedProblem.getQuestion()).isEqualTo(problem.getQuestion());
            }
        }
    }
}
package com.swm.lito.company.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanyProblem is a Querydsl query type for CompanyProblem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanyProblem extends EntityPathBase<CompanyProblem> {

    private static final long serialVersionUID = -1773710569L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanyProblem companyProblem = new QCompanyProblem("companyProblem");

    public final com.swm.lito.common.entity.QBaseEntity _super = new com.swm.lito.common.entity.QBaseEntity(this);

    public final EnumPath<com.swm.lito.company.enums.CareerStatus> careerStatus = createEnum("careerStatus", com.swm.lito.company.enums.CareerStatus.class);

    public final QCompany company;

    public final StringPath confirmation = createString("confirmation");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> dislikeCnt = createNumber("dislikeCnt", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> interviewedDate = createDateTime("interviewedDate", java.time.LocalDateTime.class);

    public final EnumPath<com.swm.lito.company.enums.InterviewStatus> interviewStatus = createEnum("interviewStatus", com.swm.lito.company.enums.InterviewStatus.class);

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    public final StringPath mood = createString("mood");

    public final QPosition position;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath rejectReason = createString("rejectReason");

    public final EnumPath<com.swm.lito.company.enums.ResultStatus> resultStatus = createEnum("resultStatus", com.swm.lito.company.enums.ResultStatus.class);

    public final StringPath review = createString("review");

    //inherited
    public final EnumPath<com.swm.lito.common.entity.BaseEntity.Status> status = _super.status;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final EnumPath<com.swm.lito.company.enums.UploadStatus> uploadStatus = createEnum("uploadStatus", com.swm.lito.company.enums.UploadStatus.class);

    public final com.swm.lito.user.domain.QUser user;

    public final NumberPath<Integer> viewCnt = createNumber("viewCnt", Integer.class);

    public QCompanyProblem(String variable) {
        this(CompanyProblem.class, forVariable(variable), INITS);
    }

    public QCompanyProblem(Path<? extends CompanyProblem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanyProblem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanyProblem(PathMetadata metadata, PathInits inits) {
        this(CompanyProblem.class, metadata, inits);
    }

    public QCompanyProblem(Class<? extends CompanyProblem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.position = inits.isInitialized("position") ? new QPosition(forProperty("position")) : null;
        this.user = inits.isInitialized("user") ? new com.swm.lito.user.domain.QUser(forProperty("user")) : null;
    }

}


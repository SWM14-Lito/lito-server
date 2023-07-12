package com.swm.lito.company.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanyProblemDetail is a Querydsl query type for CompanyProblemDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanyProblemDetail extends EntityPathBase<CompanyProblemDetail> {

    private static final long serialVersionUID = 1690004040L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanyProblemDetail companyProblemDetail = new QCompanyProblemDetail("companyProblemDetail");

    public final com.swm.lito.common.entity.QBaseEntity _super = new com.swm.lito.common.entity.QBaseEntity(this);

    public final StringPath answer = createString("answer");

    public final QCompanyProblem companyProblem;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath question = createString("question");

    //inherited
    public final EnumPath<com.swm.lito.common.entity.BaseEntity.Status> status = _super.status;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCompanyProblemDetail(String variable) {
        this(CompanyProblemDetail.class, forVariable(variable), INITS);
    }

    public QCompanyProblemDetail(Path<? extends CompanyProblemDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanyProblemDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanyProblemDetail(PathMetadata metadata, PathInits inits) {
        this(CompanyProblemDetail.class, metadata, inits);
    }

    public QCompanyProblemDetail(Class<? extends CompanyProblemDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.companyProblem = inits.isInitialized("companyProblem") ? new QCompanyProblem(forProperty("companyProblem"), inits.get("companyProblem")) : null;
    }

}


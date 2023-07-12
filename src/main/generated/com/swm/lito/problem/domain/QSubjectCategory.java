package com.swm.lito.problem.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubjectCategory is a Querydsl query type for SubjectCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubjectCategory extends EntityPathBase<SubjectCategory> {

    private static final long serialVersionUID = -1102714669L;

    public static final QSubjectCategory subjectCategory = new QSubjectCategory("subjectCategory");

    public final com.swm.lito.common.entity.QBaseEntity _super = new com.swm.lito.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final EnumPath<com.swm.lito.common.entity.BaseEntity.Status> status = _super.status;

    public final StringPath subjectCategoryName = createString("subjectCategoryName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSubjectCategory(String variable) {
        super(SubjectCategory.class, forVariable(variable));
    }

    public QSubjectCategory(Path<? extends SubjectCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubjectCategory(PathMetadata metadata) {
        super(SubjectCategory.class, metadata);
    }

}


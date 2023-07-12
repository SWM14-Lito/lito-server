package com.swm.lito.problem.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendUser is a Querydsl query type for RecommendUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendUser extends EntityPathBase<RecommendUser> {

    private static final long serialVersionUID = -126345360L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendUser recommendUser = new QRecommendUser("recommendUser");

    public final com.swm.lito.common.entity.QBaseEntity _super = new com.swm.lito.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProblem problem;

    //inherited
    public final EnumPath<com.swm.lito.common.entity.BaseEntity.Status> status = _super.status;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.swm.lito.user.domain.QUser user;

    public QRecommendUser(String variable) {
        this(RecommendUser.class, forVariable(variable), INITS);
    }

    public QRecommendUser(Path<? extends RecommendUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendUser(PathMetadata metadata, PathInits inits) {
        this(RecommendUser.class, metadata, inits);
    }

    public QRecommendUser(Class<? extends RecommendUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.problem = inits.isInitialized("problem") ? new QProblem(forProperty("problem"), inits.get("problem")) : null;
        this.user = inits.isInitialized("user") ? new com.swm.lito.user.domain.QUser(forProperty("user")) : null;
    }

}


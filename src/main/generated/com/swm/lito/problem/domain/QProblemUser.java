package com.swm.lito.problem.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProblemUser is a Querydsl query type for ProblemUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblemUser extends EntityPathBase<ProblemUser> {

    private static final long serialVersionUID = -966539757L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProblemUser problemUser = new QProblemUser("problemUser");

    public final com.swm.lito.common.entity.QBaseEntity _super = new com.swm.lito.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProblem problem;

    public final EnumPath<com.swm.lito.problem.domain.enums.ProblemStatus> problemStatus = createEnum("problemStatus", com.swm.lito.problem.domain.enums.ProblemStatus.class);

    //inherited
    public final EnumPath<com.swm.lito.common.entity.BaseEntity.Status> status = _super.status;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.swm.lito.user.domain.QUser user;

    public QProblemUser(String variable) {
        this(ProblemUser.class, forVariable(variable), INITS);
    }

    public QProblemUser(Path<? extends ProblemUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProblemUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProblemUser(PathMetadata metadata, PathInits inits) {
        this(ProblemUser.class, metadata, inits);
    }

    public QProblemUser(Class<? extends ProblemUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.problem = inits.isInitialized("problem") ? new QProblem(forProperty("problem"), inits.get("problem")) : null;
        this.user = inits.isInitialized("user") ? new com.swm.lito.user.domain.QUser(forProperty("user")) : null;
    }

}


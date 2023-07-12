package com.swm.lito.payment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPaymentUser is a Querydsl query type for PaymentUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentUser extends EntityPathBase<PaymentUser> {

    private static final long serialVersionUID = -965698765L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaymentUser paymentUser = new QPaymentUser("paymentUser");

    public final com.swm.lito.common.entity.QBaseEntity _super = new com.swm.lito.common.entity.QBaseEntity(this);

    public final com.swm.lito.company.domain.QCompanyProblem companyProblem;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final EnumPath<com.swm.lito.common.entity.BaseEntity.Status> status = _super.status;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.swm.lito.user.domain.QUser user;

    public QPaymentUser(String variable) {
        this(PaymentUser.class, forVariable(variable), INITS);
    }

    public QPaymentUser(Path<? extends PaymentUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaymentUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaymentUser(PathMetadata metadata, PathInits inits) {
        this(PaymentUser.class, metadata, inits);
    }

    public QPaymentUser(Class<? extends PaymentUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.companyProblem = inits.isInitialized("companyProblem") ? new com.swm.lito.company.domain.QCompanyProblem(forProperty("companyProblem"), inits.get("companyProblem")) : null;
        this.user = inits.isInitialized("user") ? new com.swm.lito.user.domain.QUser(forProperty("user")) : null;
    }

}


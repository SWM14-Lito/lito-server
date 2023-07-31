package com.swm.lito.core.common.entity;

import com.swm.lito.core.common.exception.ApplicationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

import static com.swm.lito.core.common.exception.StatusErrorCode.*;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private Status status = Status.ACTIVE;

    public void changeStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        ACTIVE, INACTIVE, INVALID;

        public static Status getStatus(String status) throws ApplicationException {
            if (StringUtils.hasText(status)) {
                throw new ApplicationException(EMPTY_STATUS);
            }
            try {
                return Status.valueOf(status);
            } catch (ApplicationException ignored) {
                throw new ApplicationException(INVALID_STATUS);
            }
        }
    }
}

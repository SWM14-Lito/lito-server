package com.swm.lito.user.domain;

import com.swm.lito.common.entity.BaseEntity;
import com.swm.lito.user.domain.enums.Authority;
import com.swm.lito.user.domain.enums.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "status = ACTIVE")
@SQLDelete(sql = "UPDATE user SET status = ACTIVE WHERE user_id = ?")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String name;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(columnDefinition = "TEXT")
    private String profileImgUrl;

    @Builder
    public User(Long id, String email, String name, String nickname, Provider provider) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.authority = Authority.ROLE_USER;
        this.provider = provider;
    }
}

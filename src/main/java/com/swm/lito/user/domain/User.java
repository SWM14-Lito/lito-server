package com.swm.lito.user.domain;

import com.swm.lito.common.entity.BaseEntity;
import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.user.UserErrorCode;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.user.application.port.in.request.UserRequestDto;
import com.swm.lito.user.domain.enums.Authority;
import com.swm.lito.user.domain.enums.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "status='ACTIVE'")
@SQLDelete(sql = "UPDATE user SET status = INACTIVE WHERE user_id = ?")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String oauthId;

    private String email;

    private String name;

    private String nickname;

    private String introduce;

    private int point;

    private boolean alarmStatus;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(columnDefinition = "TEXT")
    private String profileImgUrl;

    @Builder
    public User(Long id, String oauthId, String email, String name, String nickname, String introduce, Provider provider, String profileImgUrl) {
        this.id = id;
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.introduce = introduce;
        this.alarmStatus = true;
        this.authority = Authority.ROLE_USER;
        this.provider = provider;
        this.profileImgUrl = profileImgUrl;
    }

    public void validateUser(AuthUser authUser, User user) {
        if(!Objects.equals(authUser.getUserId(), user.getId())){
            throw new ApplicationException(UserErrorCode.USER_INVALID);
        }
    }

    public void change(UserRequestDto userRequestDto){
        changeNickname(userRequestDto.getNickname());
        changeProfileImgUrl(userRequestDto.getProfileImgUrl());
        changeIntroduce(userRequestDto.getIntroduce());
        changeName(userRequestDto.getName());
    }

    private void changeNickname(String nickname){
        if(StringUtils.hasText(nickname)){
            this.nickname=nickname;
        }
    }

    private void changeProfileImgUrl(String profileImgUrl){
        if(profileImgUrl!=null){
            this.profileImgUrl=profileImgUrl;
        }
    }
    private void changeIntroduce(String introduce){
        if(introduce!=null){
            this.introduce=introduce;
        }
    }

    private void changeName(String name) {
        if(name!=null){
            this.name = name;
        }
    }

    public void changeNotification(){
        this.alarmStatus = !this.alarmStatus;
    }


}

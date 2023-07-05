package com.swm.lito.user.domain;

import com.swm.lito.common.entity.BaseEntity;
import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.user.UserErrorCode;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.user.application.port.in.request.UserRequestDto;
import com.swm.lito.user.domain.enums.Authority;
import com.swm.lito.user.domain.enums.Provider;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Getter
@Entity
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "status='ACTIVE'")
@SQLDelete(sql = "UPDATE USER SET status = INACTIVE WHERE user_id = ?")
@Builder
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

    @Column(length = 1)
    private String alarmStatus;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.ROLE_USER;;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(columnDefinition = "TEXT")
    private String profileImgUrl;


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

    public void changeNotification(String alarmStatus){
        if(alarmStatus!=null){
            this.alarmStatus = alarmStatus;
        }
    }


}

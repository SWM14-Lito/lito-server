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
@SQLDelete(sql = "UPDATE USER SET status = INACTIVE WHERE id = ?")
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oauth_id")
    private String oauthId;

    @Column(length = 50)
    private String email;

    @Column(length = 10)
    private String name;

    @Column(length = 10)
    private String nickname;

    @Column(length = 150)
    private String introduce;

    private int point;

    @Column(name = "alarm_status", length = 1)
    private String alarmStatus;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.ROLE_USER;;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(name = "profile_img_url", columnDefinition = "TEXT")
    private String profileImgUrl;


    public void validateUser(AuthUser authUser, User user) {
        if(!Objects.equals(authUser.getUserId(), user.getId())){
            throw new ApplicationException(UserErrorCode.USER_INVALID);
        }
    }

    public void change(UserRequestDto userRequestDto){
        changeNickname(userRequestDto.getNickname());
        changeIntroduce(userRequestDto.getIntroduce());
        changeName(userRequestDto.getName());
    }

    private void changeNickname(String nickname){
        if(StringUtils.hasText(nickname)){
            this.nickname=nickname;
        }
    }


    private void changeIntroduce(String introduce){
        if(introduce!=null){
            this.introduce=introduce;
        }
    }

    private void changeName(String name) {
        if(StringUtils.hasText(name)){
            this.name = name;
        }
    }

    public void changeProfileImgUrl(String profileImgUrl){
        if(StringUtils.hasText(profileImgUrl)){
            this.profileImgUrl=profileImgUrl;
        }
    }

    public void changeNotification(String alarmStatus){
        if(alarmStatus!=null){
            this.alarmStatus = alarmStatus;
        }
    }


}

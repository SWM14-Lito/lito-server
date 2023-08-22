package com.swm.lito.core.user.domain;

import com.swm.lito.core.common.entity.BaseEntity;
import com.swm.lito.core.user.application.port.in.request.ProfileRequestDto;
import com.swm.lito.core.user.application.port.in.request.UserRequestDto;
import com.swm.lito.core.user.domain.enums.Authority;
import com.swm.lito.core.user.domain.enums.Provider;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

@Getter
@Entity
@Table(name = "USER",
        indexes = {
            @Index(name="idx_oauthId_provider", columnList = "oauth_id, provider"),
            @Index(name="idx_email", columnList = "email")
        }
)
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

    private String email;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String nickname;

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

    public void create(UserRequestDto userRequestDto){
        changeNickname(userRequestDto.getNickname());
        changeIntroduce(userRequestDto.getIntroduce());
        createName(userRequestDto.getName());
    }

    public void change(ProfileRequestDto profileRequestDto){
        changeNickname(profileRequestDto.getNickname());
        changeIntroduce(profileRequestDto.getIntroduce());
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

    private void createName(String name){
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

    public void deleteUser(){
        changeStatus(Status.INACTIVE);
    }


}

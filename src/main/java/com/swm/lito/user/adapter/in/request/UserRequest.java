package com.swm.lito.user.adapter.in.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    private String nickname;

    private String profileImgUrl;

    private String introduce;
}

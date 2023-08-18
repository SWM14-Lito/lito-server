package com.swm.lito.core.user.application.port.in.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequestDto {

    private String nickname;

    private String introduce;

    private String name;
}

package com.szs.refundinquiryservice.user.domain;

import com.szs.refundinquiryservice.user.domain.dto.UserSignUpResultDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Getter
@NoArgsConstructor
public class UserSignUpResponse {

    private Integer id;

    private String accessToken;

    public UserSignUpResponse(final UserSignUpResultDto userSignUpResultDto){
        this.id = userSignUpResultDto.getId();
        this.accessToken = userSignUpResultDto.getAccessToken();
    }

}

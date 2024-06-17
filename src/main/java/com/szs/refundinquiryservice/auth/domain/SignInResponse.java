package com.szs.refundinquiryservice.auth.domain;

import com.szs.refundinquiryservice.auth.domain.dto.SignInResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Getter
@NoArgsConstructor
public class SignInResponse {

    private String accessToken;

    public SignInResponse(final SignInResultDto signInResultDto){
        this.accessToken = signInResultDto.getAccessToken();
    }

}

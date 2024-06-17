package com.szs.refundinquiryservice.auth.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {

    @NotBlank(message = "사용자 아아디 값은 필수 입니다.")
    private String userId;

    @NotBlank(message = "사용자 패스워드 값은 필수 입니다.")
    private String password;

}

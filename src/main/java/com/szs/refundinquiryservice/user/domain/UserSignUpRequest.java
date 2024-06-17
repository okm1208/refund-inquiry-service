package com.szs.refundinquiryservice.user.domain;

import com.szs.refundinquiryservice.user.validator.SignUpRequestCheck;
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
public class UserSignUpRequest {

    @NotBlank(message = "사용자 아이디 값은 필수입니다.")
    private String userId;

    @NotBlank(message = "사용자 비밀번호 값은 필수입니다.")
    private String password;

    @NotBlank(message = "사용자 이름 값은 필수입니다.")
    private String name;

    @NotBlank(message = "사용자 주민등록 번호 값은 필수입니다.")
    @SignUpRequestCheck
    private String regNo;

}

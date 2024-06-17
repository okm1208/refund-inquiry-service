package com.szs.refundinquiryservice.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Getter
@AllArgsConstructor
public class UserSignUpResultDto {

    private Integer id;

    private String accessToken;
}

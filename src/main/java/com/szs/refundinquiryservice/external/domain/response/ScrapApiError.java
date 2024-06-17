package com.szs.refundinquiryservice.external.domain.response;

import lombok.Getter;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Getter
public class ScrapApiError {

    private String code;

    private String message;

    private String validations;

}

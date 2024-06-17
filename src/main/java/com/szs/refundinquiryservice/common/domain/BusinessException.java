package com.szs.refundinquiryservice.common.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author : km.oh
 * @date : 6/15/24
 */

@Getter
public class BusinessException extends RuntimeException {

    private final ApiResponseCode code;

    private final HttpStatus httpStatus;

    public BusinessException(ApiResponseCode code) {
        super(code.getDefaultMessage());
        this.code = code;
        this.httpStatus = code.getHttpStatus();
    }

    public BusinessException(ApiResponseCode code, String message) {
        super(message);
        this.code = code;
        this.httpStatus = code.getHttpStatus();
    }

}

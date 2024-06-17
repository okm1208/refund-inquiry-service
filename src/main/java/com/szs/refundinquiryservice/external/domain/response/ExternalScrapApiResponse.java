package com.szs.refundinquiryservice.external.domain.response;

import lombok.Getter;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Getter
public class ExternalScrapApiResponse<T> {

    private String status;

    private T data;

    private ScrapApiError errors;

    public boolean isSuccess() {
        return "success".equals(this.status);
    }

}

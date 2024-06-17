package com.szs.refundinquiryservice.auth.service;

import com.szs.refundinquiryservice.common.domain.ApiConstants;
import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

/**
 * @author : km.oh
 * @date : 6/16/24
 */
public class SessionScope {

    public static Integer getUserIdFromSession() {
        final String userId = MDC.get(ApiConstants.MDC_USER_ID);
        if (!StringUtils.hasText(userId)) {
            throw new BusinessException(ApiResponseCode.UNAUTHORIZED);
        }
        return Integer.parseInt(userId);
    }

}

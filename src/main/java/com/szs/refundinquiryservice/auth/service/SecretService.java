package com.szs.refundinquiryservice.auth.service;

import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import com.szs.refundinquiryservice.common.secret.AESUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Service
public class SecretService {

    @Value("${secret.aes.secretKey}")
    private String secretKey;

    public String decryptRegNo(String encryptRegNo) throws Exception {
        try {
            return AESUtil.decrypt(encryptRegNo, secretKey);
        } catch (Exception e) {
            throw new BusinessException(ApiResponseCode.ERROR);
        }

    }

    public String encryptRegNo(String regNo) {
        try {
            return AESUtil.encrypt(regNo, secretKey);
        } catch (Exception e) {
            throw new BusinessException(ApiResponseCode.ERROR);
        }
    }

}

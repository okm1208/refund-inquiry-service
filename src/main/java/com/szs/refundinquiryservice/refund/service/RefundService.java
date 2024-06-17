package com.szs.refundinquiryservice.refund.service;

import com.szs.refundinquiryservice.common.data.entity.User;
import com.szs.refundinquiryservice.common.data.entity.UserIncome;
import com.szs.refundinquiryservice.common.data.entity.UserTaxDeduction;
import com.szs.refundinquiryservice.common.data.repository.UserRepository;
import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import com.szs.refundinquiryservice.refund.domain.dto.RefundAmountResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Service
@RequiredArgsConstructor
public class RefundService {

    private final UserRepository userRepository;

    private final TaxCalculateService taxCalculateService;

    @Transactional(readOnly = true)
    public RefundAmountResultDto getRefundAmountByUser(final Integer userId) {

        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.NOT_FOUND, "사용자가 존재 하지 않습니다."));


        final UserIncome userIncome = user.getUserIncomeList().stream().findFirst()
                .orElseThrow(() -> new BusinessException(ApiResponseCode.NOT_FOUND, "종합소득 정보가 존재하지 않습니다."));

        final BigDecimal finalTaxAmount = taxCalculateService.calculateFinalTaxAmount(userIncome, user.getUserTaxDedutionList());

        return new RefundAmountResultDto(finalTaxAmount);
    }

}

package com.szs.refundinquiryservice.refund.service;

import com.szs.refundinquiryservice.common.data.entity.UserIncome;
import com.szs.refundinquiryservice.common.data.entity.UserTaxDeduction;
import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import com.szs.refundinquiryservice.refund.domain.IncomeTaxRangeDto;
import com.szs.refundinquiryservice.refund.util.IncomeTaxRangeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.szs.refundinquiryservice.refund.util.TaxCalculateUtil.*;

/**
 * @author : km.oh
 * @date : 6/17/24
 */
@Service
public class TaxCalculateService {

    @Transactional(readOnly = true)
    public BigDecimal calculateFinalTaxAmount(final UserIncome userIncome, final List<UserTaxDeduction> userTaxDedutionList){

        // 종합소득 금액
        final BigDecimal comprehensiveIncomeAmount = userIncome.getComprehensiveIncomeAmount();

        // 소득 공제
        final UserTaxDeduction userTaxDeduction = userTaxDedutionList.stream().findFirst()
                .orElseThrow(() -> new BusinessException(ApiResponseCode.NOT_FOUND, "소득공제 정보가 존재하지 않습니다."));
        final BigDecimal taxDeductionSum = userTaxDeduction.getUserTaxDedutionMonthList()
                .stream()
                .map(v -> v.getTaxDeductionAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // 과세 표준
        final BigDecimal taxBaseAmount = calculateTaxBaseAmount(comprehensiveIncomeAmount, taxDeductionSum);

        // 세율 구간
        final IncomeTaxRangeDto incomeTaxRange = IncomeTaxRangeUtil.findIncomeTexRangeBase(taxBaseAmount);

        // 산출 세액
        final BigDecimal taxAmount = calculateTaxAmount(taxBaseAmount, incomeTaxRange);

        // 세액 공제
        final BigDecimal taxDeductionAmount = userTaxDeduction.getTaxDeductionAmount();

        // 결정 세액
        final BigDecimal finalTaxAmount = calculateDeterminedTaxAmount(taxAmount, taxDeductionAmount);

        return finalTaxAmount;
    }
}

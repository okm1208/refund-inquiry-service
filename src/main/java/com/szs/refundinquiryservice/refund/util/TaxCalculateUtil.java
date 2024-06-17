package com.szs.refundinquiryservice.refund.util;

import com.szs.refundinquiryservice.refund.domain.IncomeTaxRangeDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author : km.oh
 * @date : 6/16/24
 */
public class TaxCalculateUtil {

    // 과세 표준 계산 = 종합소득금액 - 소득공제
    public static BigDecimal calculateTaxBaseAmount(final BigDecimal comprehensiveIncomeAmount,
                                                    final BigDecimal totalIncomeDeductionAmount) {
        final BigDecimal taxBaseAmount = subtractRounded(comprehensiveIncomeAmount, totalIncomeDeductionAmount);

        if (taxBaseAmount.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return taxBaseAmount;
    }

    // 산출 세액 = 과세표준 * 기본 세율
    public static BigDecimal calculateTaxAmount(final BigDecimal taxBaseAmount,
                                                final IncomeTaxRangeDto incomeTaxRange) {

        // 과세 표준 초과 금액
        final BigDecimal overTaxBaseAmount = subtractRounded(taxBaseAmount, incomeTaxRange.getStartAmount());
        // 기본 세율
        final BigDecimal basicTaxRageAmount = multiplyFirstArgRounded(overTaxBaseAmount, incomeTaxRange.getBasicTaxRate());

        return addRounded(basicTaxRageAmount, incomeTaxRange.getBasicTaxAmount());
    }

    // 결정 새액 = 산출세액 - 세액공제
    public static BigDecimal calculateDeterminedTaxAmount(final BigDecimal taxAmount,
                                                          final BigDecimal taxDeductionAmount) {
        return subtractRounded(taxAmount, taxDeductionAmount);
    }

    public static BigDecimal addRounded(BigDecimal a, BigDecimal b) {
        a = a.setScale(0, RoundingMode.HALF_UP);
        b = b.setScale(0, RoundingMode.HALF_UP);
        return a.add(b);
    }

    public static BigDecimal subtractRounded(BigDecimal a, BigDecimal b) {
        a = a.setScale(0, RoundingMode.HALF_UP);
        b = b.setScale(0, RoundingMode.HALF_UP);
        return a.subtract(b);
    }

    public static BigDecimal multiplyRounded(BigDecimal a, BigDecimal b) {
        a = a.setScale(0, RoundingMode.HALF_UP);
        b = b.setScale(0, RoundingMode.HALF_UP);
        return a.multiply(b);
    }

    public static BigDecimal multiplyFirstArgRounded(BigDecimal a, BigDecimal b) {
        a = a.setScale(0, RoundingMode.HALF_UP);
        return a.multiply(b);
    }

}

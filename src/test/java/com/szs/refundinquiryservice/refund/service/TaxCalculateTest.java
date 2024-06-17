package com.szs.refundinquiryservice.refund.service;

import com.szs.refundinquiryservice.common.data.entity.UserIncome;
import com.szs.refundinquiryservice.refund.domain.IncomeTaxRangeDto;
import com.szs.refundinquiryservice.refund.util.IncomeTaxRangeUtil;
import com.szs.refundinquiryservice.refund.util.TaxCalculateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.szs.refundinquiryservice.refund.util.TaxCalculateUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : km.oh
 * @date : 6/17/24
 */

public class TaxCalculateTest {

    /**
     * 과세 표준 : 종합 소득 금액 + 소득 공제
     */
    @Test
    public void testTaxBaseCalculate() {
        BigDecimal comprehensiveIncomeAmount = new BigDecimal("20000000.5");
        BigDecimal totalIncomeDeductionAmount = new BigDecimal("158000.2");

        BigDecimal result = calculateTaxBaseAmount(comprehensiveIncomeAmount, totalIncomeDeductionAmount);

        assertEquals(new BigDecimal("19842001"), result);

        comprehensiveIncomeAmount = new BigDecimal("20000000.5");
        totalIncomeDeductionAmount = new BigDecimal("158000.7");

        result = calculateTaxBaseAmount(comprehensiveIncomeAmount, totalIncomeDeductionAmount);
        assertEquals(new BigDecimal("19842000"), result);

        comprehensiveIncomeAmount = new BigDecimal("15000000.2");
        totalIncomeDeductionAmount = new BigDecimal("58000.2");

        result = calculateTaxBaseAmount(comprehensiveIncomeAmount, totalIncomeDeductionAmount);
        assertEquals(new BigDecimal("14942000"), result);
    }

    /**
     * 산출 세액 : 과세 표준 * 기본 세율
     */
    @Test
    public void testTaxAmountCalculate() {

        // CASE : 1400만 초과~ 5000만 이하
        // 84만원 + 1400만 초과 금액의 15%
        BigDecimal taxBaseAmount = new BigDecimal("14000100");

        IncomeTaxRangeDto incomeTaxRange = IncomeTaxRangeUtil.findIncomeTexRangeBase(taxBaseAmount);

        BigDecimal taxAmount = calculateTaxAmount(taxBaseAmount, incomeTaxRange);

        // result : 84만원  + 14.5원
        assertEquals(new BigDecimal("840015"), taxAmount);

        // CASE : 3억 초과~ 5억원 이하
        // 9,406만원 + 3억원 초과 금액의 40%
        taxBaseAmount = new BigDecimal("325000000");
        incomeTaxRange = IncomeTaxRangeUtil.findIncomeTexRangeBase(taxBaseAmount);

        // result : 9406만원  + 10,000,000원
        taxAmount = calculateTaxAmount(taxBaseAmount, incomeTaxRange);
        assertEquals(new BigDecimal("104060000"), taxAmount);


        // CASE : 1400만 이하
        // 과세표준의 6%
        taxBaseAmount = new BigDecimal("11000000");
        incomeTaxRange = IncomeTaxRangeUtil.findIncomeTexRangeBase(taxBaseAmount);

        // result : 0  + 660,000원
        taxAmount = calculateTaxAmount(taxBaseAmount, incomeTaxRange);
        assertEquals(new BigDecimal("660000"), taxAmount);


        // CASE : 10억원 초과
        // 38,406만원 + 10억원 초과금액의 45%
        taxBaseAmount = new BigDecimal("1320001000");
        incomeTaxRange = IncomeTaxRangeUtil.findIncomeTexRangeBase(taxBaseAmount);

        taxAmount = calculateTaxAmount(taxBaseAmount, incomeTaxRange);
        assertEquals(new BigDecimal("528060450"), taxAmount);
    }

}

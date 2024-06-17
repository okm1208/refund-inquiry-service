package com.szs.refundinquiryservice.refund.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author : km.oh
 * @date : 6/16/24
 */
@Getter
@AllArgsConstructor
public class IncomeTaxRangeDto {

    private BigDecimal startAmount;

    private BigDecimal endAmount;

    private BigDecimal basicTaxRate;

    private BigDecimal basicTaxAmount;

    public BigDecimal getStartAmount() {
        if (BigDecimal.ZERO.equals(startAmount)) {
            return startAmount;
        }
        return startAmount.subtract(new BigDecimal(1));
    }

}

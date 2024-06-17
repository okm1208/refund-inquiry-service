package com.szs.refundinquiryservice.refund.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author : km.oh
 * @date : 6/16/24
 */
@Getter
@AllArgsConstructor
public class RefundAmountResultDto {

    private BigDecimal determinedTaxAmount;

}

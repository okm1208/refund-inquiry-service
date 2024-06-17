package com.szs.refundinquiryservice.refund.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.szs.refundinquiryservice.refund.domain.dto.RefundAmountResultDto;
import lombok.Getter;

import java.text.NumberFormat;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Getter
public class GetRefundResponse {

    @JsonProperty("결정세액")
    private String determinedTaxAmount;

    public GetRefundResponse(final RefundAmountResultDto refundAmountResultDto) {
        this.determinedTaxAmount = NumberFormat.getInstance()
                .format(refundAmountResultDto.getDeterminedTaxAmount());;
    }

}

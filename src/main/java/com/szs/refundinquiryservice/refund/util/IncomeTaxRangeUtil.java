package com.szs.refundinquiryservice.refund.util;

import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import com.szs.refundinquiryservice.refund.domain.IncomeTaxRangeDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author : km.oh
 * @date : 6/16/24
 */
public class IncomeTaxRangeUtil {

    public static List<IncomeTaxRangeDto> INCODE_TAX_RANGE_LIST = Arrays.asList(
            new IncomeTaxRangeDto(BigDecimal.ZERO, new BigDecimal(14_000_000), new BigDecimal("0.06"), new BigDecimal(0)),
            new IncomeTaxRangeDto(new BigDecimal(14_000_001), new BigDecimal(50_000_000), new BigDecimal("0.15"), new BigDecimal(840_000)),
            new IncomeTaxRangeDto(new BigDecimal(500_00_001), new BigDecimal(88_000_000), new BigDecimal("0.24"), new BigDecimal(6_240_000)),
            new IncomeTaxRangeDto(new BigDecimal(88_000_001), new BigDecimal(150_000_000), new BigDecimal("0.35"), new BigDecimal(15_360_000)),
            new IncomeTaxRangeDto(new BigDecimal(150_000_001), new BigDecimal(300_000_000), new BigDecimal("0.38"), new BigDecimal(37_060_000)),
            new IncomeTaxRangeDto(new BigDecimal(300_000_001), new BigDecimal(500_000_000), new BigDecimal("0.40"), new BigDecimal(94_060_000)),
            new IncomeTaxRangeDto(new BigDecimal(500_000_001), new BigDecimal(1_000_000_000), new BigDecimal("0.42"), new BigDecimal(174_060_000)),
            new IncomeTaxRangeDto(new BigDecimal(1_000_000_001), new BigDecimal(Integer.MAX_VALUE), new BigDecimal("0.45"), new BigDecimal(384_060_000))
    );


    public static IncomeTaxRangeDto findIncomeTexRangeBase(final BigDecimal taxBaseAmount) {
        // 0보다 작으면 음수
        // 0보다 크면 양수
        return INCODE_TAX_RANGE_LIST.stream()
                .filter(v -> {
                    if ((taxBaseAmount.compareTo(v.getStartAmount()) >= 0)
                            && (taxBaseAmount.compareTo(v.getEndAmount())) <= 0) {
                        return true;
                    }
                    return false;
                })
                .findFirst()
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ERROR));
    }

}

package com.szs.refundinquiryservice.external.domain.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Getter
public class ScrapApiData {

    @JsonAlias("종합소득금액")
    private BigDecimal comprehensiveIncomeAmount;

    @JsonAlias("이름")
    private String name;

    @JsonAlias("소득공제")
    private IncomeDeduction incomeDeduction;

    @Getter
    public static class IncomeDeduction {

        @JsonAlias("세액공제")
        private String taxDeductionAmount;

        @JsonAlias("국민연금")
        private List<NssTaxDeduction> nssTaxDeductionList = new ArrayList<>();

        @JsonAlias("신용카드소득공제")
        private CreditCardTaxDeduction creditCardTaxDeduction;

        public BigDecimal convertTaxDeductionAmountToBigDecimal() {
            if (!StringUtils.hasText(taxDeductionAmount)) {
                return BigDecimal.valueOf(0L);
            }
            return new BigDecimal(taxDeductionAmount.replaceAll(",", ""));
        }

        public String getBaseYear() {
            if (this.creditCardTaxDeduction != null) {
                return String.valueOf(creditCardTaxDeduction.year);
            }
            if (this.nssTaxDeductionList != null) {
                final NssTaxDeduction firstNssTaxDeduction = this.nssTaxDeductionList.stream()
                        .findFirst()
                        .orElse(null);
                if (firstNssTaxDeduction != null) {
                    String[] nssTaxDeductionArray = firstNssTaxDeduction.yearMonth.split("-");
                    if (nssTaxDeductionArray != null && nssTaxDeductionArray.length > 0) {
                        return nssTaxDeductionArray[0];
                    }
                }
            }
            throw new BusinessException(ApiResponseCode.EXTERNAL_SERVER_ERROR, "세액공제 기준년도를 추출할 수 업습니다.");
        }

    }

    @Getter
    public static class NssTaxDeduction {

        @JsonAlias("월")
        private String yearMonth;

        @JsonAlias("공제액")
        private String deductionAmount;

        public BigDecimal convertDeductionAmountToBigDecimal() {
            if (!StringUtils.hasText(deductionAmount)) {
                return BigDecimal.valueOf(0L);
            }
            return new BigDecimal(deductionAmount.replaceAll(",", ""));
        }

    }

    @Getter
    public static class CreditCardTaxDeduction {

        @JsonAlias("year")
        private Integer year;

        @JsonAlias("month")
        private List<Map<String, String>> monthList = new ArrayList<>();


        public List<CreditCardTaxDeductionPojo> convertMonthListToStringList() {
            final Integer finalYear = year;
            return monthList.stream()
                    .map(v -> v.entrySet().stream().iterator().next())
                    .map(v -> {
                        final String month = v.getKey();
                        final String deductionAmount = v.getValue();
                        return new CreditCardTaxDeductionPojo(finalYear, month, deductionAmount);
                    }).collect(Collectors.toList());
        }

        @Getter
        public static class CreditCardTaxDeductionPojo {

            private String yearMonth;

            private String deductionAmount;

            public BigDecimal convertDeductionAmountToBigDecimal() {
                if (!StringUtils.hasText(deductionAmount)) {
                    return BigDecimal.valueOf(0L);
                }
                return new BigDecimal(deductionAmount.replaceAll(",", ""));
            }

            public CreditCardTaxDeductionPojo(final int year, final String month, final String deductionAmount) {
                this.yearMonth = year + "-" + month;
                this.deductionAmount = deductionAmount;

            }

        }

    }

}

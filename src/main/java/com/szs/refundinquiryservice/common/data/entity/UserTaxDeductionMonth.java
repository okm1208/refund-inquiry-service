package com.szs.refundinquiryservice.common.data.entity;

import com.szs.refundinquiryservice.common.domain.enums.TaxDeductionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_tax_deduction_month")
@Comment("고객 소득 공제 정보 테이블 ( 월별 상세 )")
public class UserTaxDeductionMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 7, nullable = false)
    @Comment(value = "기준 년월")
    private String baseYearMonth;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Comment(value = "공제액")
    private BigDecimal taxDeductionAmount;

    @Enumerated(EnumType.STRING)
    private TaxDeductionType taxDeductionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tax_deduction_id")
    private UserTaxDeduction userTaxDeduction;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Comment(value = "생성일")
    private LocalDateTime createdDatetime;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Comment(value = "수정일")
    private LocalDateTime modifiedDatetime;

    public UserTaxDeductionMonth(final User user,
                                 final String baseYearMonth,
                                 final BigDecimal taxDeductionAmount,
                                 final TaxDeductionType taxDeductionType,
                                 final UserTaxDeduction userTaxDeduction) {
        this.user = user;
        this.baseYearMonth = baseYearMonth;
        this.taxDeductionAmount = taxDeductionAmount;
        this.taxDeductionType = taxDeductionType;
        this.userTaxDeduction = userTaxDeduction;
        this.createdDatetime = LocalDateTime.now();
        this.modifiedDatetime = LocalDateTime.now();

    }

}

package com.szs.refundinquiryservice.common.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_tax_deduction")
@Comment("고객 소득 공제 정보 테이블")
public class UserTaxDeduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Comment(value = "세액공제 금액")
    private BigDecimal taxDeductionAmount;

    @Comment(value = "세액공제 기준년")
    private String baseYear;

    @OneToMany(mappedBy = "userTaxDeduction", cascade = CascadeType.PERSIST)
    @Comment(value = "고객 년월 세액공제 정보")
    private List<UserTaxDeductionMonth> userTaxDedutionMonthList = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Comment(value = "생성일")
    private LocalDateTime createdDatetime;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Comment(value = "수정일")
    private LocalDateTime modifiedDatetime;

    public UserTaxDeduction(final User user,
                            final BigDecimal taxDeductionAmount,
                            final String baseYear,
                            final List<UserTaxDeductionMonth> userTaxDedutionMonthList) {
        this.user = user;
        this.taxDeductionAmount = taxDeductionAmount;
        this.baseYear = baseYear;
        this.userTaxDedutionMonthList = userTaxDedutionMonthList;
        this.createdDatetime = LocalDateTime.now();
        this.modifiedDatetime = LocalDateTime.now();
    }

    public void addUserTaxDeductionMonth(final List<UserTaxDeductionMonth> userTaxDedutionMonthList) {
        this.userTaxDedutionMonthList.addAll(userTaxDedutionMonthList);
    }

}

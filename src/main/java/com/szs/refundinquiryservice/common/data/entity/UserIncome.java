package com.szs.refundinquiryservice.common.data.entity;

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
@Table(name = "user_income_info")
@Comment("고객 소득 정보 테이블 (년 단위)")
public class UserIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Comment(value = "세액공제 기준년")
    private String baseYear;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Comment(value = "종합소득금액")
    private BigDecimal comprehensiveIncomeAmount;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Comment(value = "생성일")
    private LocalDateTime createdDatetime;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Comment(value = "수정일")
    private LocalDateTime modifiedDatetime;

    public UserIncome(final User user, final String baseYear, final BigDecimal comprehensiveIncomeAmount) {
        this.user = user;
        this.baseYear = baseYear;
        this.comprehensiveIncomeAmount = comprehensiveIncomeAmount;
        this.createdDatetime = LocalDateTime.now();
        this.modifiedDatetime = LocalDateTime.now();
    }

    public void updateBaseYear(final String baseYear) {
        this.baseYear = baseYear;
    }

}

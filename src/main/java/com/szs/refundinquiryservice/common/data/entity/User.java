package com.szs.refundinquiryservice.common.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

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
@Table(name = "user_info")
@Comment("고객 정보 테이블")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false, unique = true)
    @Comment(value = "아이디")
    private String userId;

    @Column(length = 200, nullable = false)
    @Comment(value = "패스워드")
    private String password;

    @Column(length = 50, nullable = false)
    @Comment(value = "이름")
    private String name;

    @Column(length = 64, nullable = false)
    @Comment(value = "주민등록번호")
    private String regNo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    @Comment(value = "고객 년 소득 정보")
    private List<UserIncome> userIncomeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    @Comment(value = "고객 년 세액공제 정보")
    private List<UserTaxDeduction> userTaxDedutionList = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Comment(value = "생성일")
    private LocalDateTime createdDatetime;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Comment(value = "수정일")
    private LocalDateTime modifiedDatetime;

    @Column(nullable = false, length = 32)
    private String salt;


    public User(final String userId,
                final String hashedPassword,
                final String name,
                final String hashedRegNo,
                final String salt) {

        this.userId = userId;
        this.password = hashedPassword;
        this.name = name;
        this.regNo = hashedRegNo;
        this.salt = salt;
        this.createdDatetime = LocalDateTime.now();
        this.modifiedDatetime = LocalDateTime.now();
    }

}

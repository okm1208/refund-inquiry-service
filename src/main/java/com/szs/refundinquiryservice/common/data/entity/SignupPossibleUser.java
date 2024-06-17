package com.szs.refundinquiryservice.common.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "signup_possible_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "signup_possible_user_table_unique_01",
                        columnNames = {"name", "regNo"}
                )
        }
)
@Comment("가입 가능한 고객정보 테이블")
public class SignupPossibleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    @Comment(value = "이름")
    private String name;

    @Column(length = 14, nullable = false)
    @Comment(value = "주민등록번호")
    private String regNo;


}

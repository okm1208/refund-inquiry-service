package com.szs.refundinquiryservice.common.data.repository;

import com.szs.refundinquiryservice.common.data.entity.User;
import com.szs.refundinquiryservice.common.data.entity.UserIncome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
public interface UserIncomeRepository extends JpaRepository<UserIncome, Integer> {

    Optional<UserIncome> findUserIncomeByUserAndBaseYear(final User user, final String baseYear);

}

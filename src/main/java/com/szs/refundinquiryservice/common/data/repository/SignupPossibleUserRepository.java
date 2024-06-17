package com.szs.refundinquiryservice.common.data.repository;

import com.szs.refundinquiryservice.common.data.entity.SignupPossibleUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
public interface SignupPossibleUserRepository extends JpaRepository<SignupPossibleUser, Integer> {

    Optional<SignupPossibleUser> findSignupPossibleUserByRegNo(final String regNo);

}

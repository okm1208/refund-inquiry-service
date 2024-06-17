package com.szs.refundinquiryservice.common.data.repository;

import com.szs.refundinquiryservice.common.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByRegNo(final String regNo);

    Optional<User> findUserByUserId(final String userId);

}

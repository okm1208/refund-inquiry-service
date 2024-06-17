package com.szs.refundinquiryservice.user.service;

import com.szs.refundinquiryservice.common.domain.BusinessException;
import com.szs.refundinquiryservice.user.domain.UserSignUpRequest;
import com.szs.refundinquiryservice.user.domain.dto.UserSignUpResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testSignupValidator() {

        final UserSignUpRequest userSignUpRequest =
                new UserSignUpRequest("okm12", "1234", "동탁", "921108-1582815");

        Throwable exception = assertThrows(BusinessException.class, () -> {
            userService.signup(userSignUpRequest);
        });
        assertEquals("가입 불가능한 주민등록 번호입니다.", exception.getMessage());

        final UserSignUpRequest userSignUpRequest2 =
                new UserSignUpRequest("okm12", "1234", "동탁", "921108-1582816");

        final UserSignUpResultDto userSignUpResultDto = userService.signup(userSignUpRequest2);

        assertNotNull(userSignUpResultDto);

        final UserSignUpRequest userSignUpRequest3 =
                new UserSignUpRequest("okm12", "1234", "동탁", "921108-1582816");

        exception = assertThrows(BusinessException.class, () -> {
            userService.signup(userSignUpRequest3);
        });
        assertEquals("이미 가입된 회원입니다.", exception.getMessage());

    }

}
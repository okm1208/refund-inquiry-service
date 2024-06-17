package com.szs.refundinquiryservice.auth.service;

import com.szs.refundinquiryservice.auth.domain.SignInRequest;
import com.szs.refundinquiryservice.auth.domain.dto.SignInResultDto;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import com.szs.refundinquiryservice.user.domain.UserSignUpRequest;
import com.szs.refundinquiryservice.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Test
    @Transactional
    public void testSignIn() {

        final String userId = "okm12";
        final String password = "test1234";


        final SignInRequest signInRequest = new SignInRequest(userId, password);
        Throwable exception = assertThrows(BusinessException.class, () -> {
            authService.signIn(signInRequest);
        });

        assertEquals("사용자가 존재 하지 않습니다.", exception.getMessage());

        final String name = "동탁";
        final String regNo = "921108-1582816";

        userService.signup(new UserSignUpRequest(userId, password, name, regNo));

        final SignInResultDto signInResultDto = authService.signIn(signInRequest);

        assertNotNull(signInResultDto);
        assertNotNull(signInResultDto.getAccessToken());
    }

}
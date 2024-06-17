package com.szs.refundinquiryservice.auth.controller;

import com.szs.refundinquiryservice.auth.domain.SignInRequest;
import com.szs.refundinquiryservice.auth.domain.SignInResponse;
import com.szs.refundinquiryservice.auth.domain.dto.SignInResultDto;
import com.szs.refundinquiryservice.auth.service.AuthService;
import com.szs.refundinquiryservice.common.domain.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@RestController
@RequestMapping("/szs")
@Tag(name = "Auth", description = "인증")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인")
    public ApiResponse<SignInResponse> login(@RequestBody @Valid SignInRequest signInRequest) {

        return ApiResponse.success(
                new SignInResponse(authService.signIn(signInRequest))
        );
    }

}

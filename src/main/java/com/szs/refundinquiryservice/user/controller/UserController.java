package com.szs.refundinquiryservice.user.controller;

import com.szs.refundinquiryservice.common.domain.ApiResponse;
import com.szs.refundinquiryservice.user.domain.UserSignUpRequest;
import com.szs.refundinquiryservice.user.domain.UserSignUpResponse;
import com.szs.refundinquiryservice.user.domain.dto.UserSignUpResultDto;
import com.szs.refundinquiryservice.user.service.UserService;
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
 * @since : 6/15/24
 **/
@RestController
@RequestMapping("/szs")
@Tag(name = "User", description = "회원")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "서비스에 가입합니다. ( 특정 사용자만 가능 )")
    public ApiResponse<UserSignUpResponse> signup(@RequestBody @Valid UserSignUpRequest userSignUpRequest) {
        return ApiResponse.success(
                new UserSignUpResponse(userService.signup(userSignUpRequest))
        );
    }

}

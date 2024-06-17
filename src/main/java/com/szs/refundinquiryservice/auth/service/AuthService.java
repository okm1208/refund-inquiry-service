package com.szs.refundinquiryservice.auth.service;

import com.szs.refundinquiryservice.auth.domain.SignInRequest;
import com.szs.refundinquiryservice.auth.domain.dto.SignInResultDto;
import com.szs.refundinquiryservice.common.data.entity.User;
import com.szs.refundinquiryservice.common.data.repository.UserRepository;
import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import com.szs.refundinquiryservice.common.secret.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    @Transactional
    public SignInResultDto signIn(final SignInRequest signInRequest) {

        final User user = userRepository.findUserByUserId(signInRequest.getUserId())
                .orElseThrow(() -> new BusinessException(ApiResponseCode.NOT_FOUND, "사용자가 존재 하지 않습니다."));

        if (!PasswordUtil.validatePassword(signInRequest.getPassword(), user.getPassword())) {
            throw new BusinessException(ApiResponseCode.UNAUTHENTICATED, "아이디 혹은 비밀번호를 다시 확인해주세요.");
        }

        return new SignInResultDto(tokenService.issueAccessToken(user.getId()));
    }

}

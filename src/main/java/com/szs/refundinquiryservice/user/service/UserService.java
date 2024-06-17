package com.szs.refundinquiryservice.user.service;

import com.szs.refundinquiryservice.auth.service.SecretService;
import com.szs.refundinquiryservice.auth.service.TokenService;
import com.szs.refundinquiryservice.common.data.entity.User;
import com.szs.refundinquiryservice.common.data.repository.SignupPossibleUserRepository;
import com.szs.refundinquiryservice.common.data.repository.UserRepository;
import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import com.szs.refundinquiryservice.common.secret.PasswordUtil;
import com.szs.refundinquiryservice.common.secret.SaltUtil;
import com.szs.refundinquiryservice.user.domain.UserSignUpRequest;
import com.szs.refundinquiryservice.user.domain.dto.UserSignUpResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final SignupPossibleUserRepository signupPossibleUserRepository;

    private final UserRepository userRepository;

    private final SecretService secretService;

    private final TokenService tokenService;

    @Transactional
    public UserSignUpResultDto signup(final UserSignUpRequest userSignUpRequest) {

        validateSignup(userSignUpRequest.getRegNo());

        final User user = createUserEntity(userSignUpRequest);

        userRepository.save(user);

        final String accessToken = tokenService.issueAccessToken(user.getId());

        return new UserSignUpResultDto(user.getId(), accessToken);
    }

    private User createUserEntity(final UserSignUpRequest userSignUpRequest) {

        final String userPasswordSalt = SaltUtil.generateSalt(16);

        return new User(userSignUpRequest.getUserId(),
                PasswordUtil.createHash(userSignUpRequest.getPassword(), userPasswordSalt),
                userSignUpRequest.getName(),
                secretService.encryptRegNo(userSignUpRequest.getRegNo()),
                userPasswordSalt);
    }

    private void validateSignup(final String regNo) {

        final String encryptRegNo = secretService.encryptRegNo(regNo);

        userRepository.findUserByRegNo(encryptRegNo).ifPresent(user -> {
            throw new BusinessException(ApiResponseCode.BAD_REQUEST, "이미 가입된 회원입니다.");
        });

        signupPossibleUserRepository.findSignupPossibleUserByRegNo(regNo)
                .orElseThrow(() ->
                        new BusinessException(ApiResponseCode.BAD_REQUEST, "가입 불가능한 주민등록 번호입니다.")
                );
    }

}

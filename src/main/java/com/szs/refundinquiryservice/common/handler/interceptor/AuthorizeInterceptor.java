package com.szs.refundinquiryservice.common.handler.interceptor;

import com.szs.refundinquiryservice.auth.service.TokenService;
import com.szs.refundinquiryservice.common.domain.ApiConstants;
import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Slf4j
public class AuthorizeInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZE_HEADER_PREFIX = "Bearer ";

    private final TokenService tokenService;

    public AuthorizeInterceptor(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        SecureRole secureRole = handlerMethod.getMethodAnnotation(SecureRole.class);
        if (secureRole == null) {
            return true;
        }

        String accessToken = extractAccessTokenFromHeader(request.getHeader(ApiConstants.ACCESS_TOKEN_HEADER_NAME));
        if (!StringUtils.hasText(accessToken)) {
            throw new BusinessException(ApiResponseCode.UNAUTHORIZED);
        }

        String userId = tokenService.extractUserIdFromToken(accessToken);

        MDC.put(ApiConstants.MDC_USER_ID, userId);
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {

    }

    private String extractAccessTokenFromHeader(final String authorizationHeaderValue) {
        if (StringUtils.isEmpty(authorizationHeaderValue) || authorizationHeaderValue.length() < AUTHORIZE_HEADER_PREFIX.length()) {
            throw new BusinessException(ApiResponseCode.UNAUTHORIZED, "올바른 토큰 정보가 아닙니다.");
        }
        return authorizationHeaderValue.substring(AUTHORIZE_HEADER_PREFIX.length());
    }
}
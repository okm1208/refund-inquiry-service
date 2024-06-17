package com.szs.refundinquiryservice.common.config;

import com.szs.refundinquiryservice.auth.service.TokenService;
import com.szs.refundinquiryservice.common.handler.interceptor.AuthorizeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${secret.jwt.secret}")
    private String secret;

    private final TokenService tokenService;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizeInterceptor(tokenService))
                .addPathPatterns("/szs/**");

    }

}

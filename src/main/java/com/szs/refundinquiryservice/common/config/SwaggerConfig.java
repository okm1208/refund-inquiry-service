package com.szs.refundinquiryservice.common.config;

import com.szs.refundinquiryservice.common.domain.ApiConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "환급 조회 API 명세서",
                description = "사용자의 환급액을 계산하기 위한 API 기능 정의",
                version = "v1"
        )
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi defaultGroup() {
        return GroupedOpenApi.builder()
                .group("refund-inquiry-default")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes(
                                "accessToken",
                                new SecurityScheme()
                                        .name(ApiConstants.ACCESS_TOKEN_HEADER_NAME)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                        )
                ).addSecurityItem(new SecurityRequirement().addList("accessToken"));
    }

}

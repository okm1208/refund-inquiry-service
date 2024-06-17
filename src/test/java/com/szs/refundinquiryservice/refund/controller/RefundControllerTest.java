package com.szs.refundinquiryservice.refund.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szs.refundinquiryservice.auth.domain.SignInRequest;
import com.szs.refundinquiryservice.auth.domain.SignInResponse;
import com.szs.refundinquiryservice.common.domain.ApiConstants;
import com.szs.refundinquiryservice.common.domain.ApiResponse;
import com.szs.refundinquiryservice.common.handler.interceptor.AuthorizeInterceptor;
import com.szs.refundinquiryservice.scrap.domain.ScrapRequest;
import com.szs.refundinquiryservice.user.domain.UserSignUpRequest;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RefundControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void testTokenValidation() throws Exception {
        ScrapRequest request =
                new ScrapRequest("동탁", "921108-1582816");

        // 스크래핑 요청
        String jsonBody = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/szs/scrap")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody)
                )
                .andExpect(status().isForbidden())
                .andReturn();


        // 가입
        UserSignUpRequest userSignUpRequest =
                new UserSignUpRequest("okm12", "test1234", "동탁", "921108-1582816");
        jsonBody = objectMapper.writeValueAsString(userSignUpRequest);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/szs/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody)
                )
                .andExpect(status().isOk())
                .andReturn();


        // 로그인
        SignInRequest signInRequest =
                new SignInRequest("okm12", "test1234");
        jsonBody = objectMapper.writeValueAsString(userSignUpRequest);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/szs/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody)
                )
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JavaType type = objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, SignInResponse.class);

        ApiResponse<SignInResponse> response = objectMapper.readValue(content, type);

        String accessToken = response.getData().getAccessToken();

        jsonBody = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/szs/scrap")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(ApiConstants.ACCESS_TOKEN_HEADER_NAME, AuthorizeInterceptor.AUTHORIZE_HEADER_PREFIX + accessToken)
                                .content(jsonBody)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

}
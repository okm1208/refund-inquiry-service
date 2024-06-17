package com.szs.refundinquiryservice.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szs.refundinquiryservice.user.domain.UserSignUpRequest;
import org.junit.jupiter.api.Test;
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
class UserSignupValidatorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void testSignupValidation() throws Exception {

        // CASE : 주민 번호 유효성 검사
        UserSignUpRequest request =
                new UserSignUpRequest("okm12", "test1234", "오경무", "123456");

        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/szs/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody)
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        // CASE : 필수 값 누락
        request =
                new UserSignUpRequest("okm12", "", "오경무", "123456");

        jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/szs/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody)
                )
                .andExpect(status().isBadRequest())
                .andReturn();


        // 성공
        request =
                new UserSignUpRequest("okm12", "1234", "동탁", "921108-1582816");

        jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/szs/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody)
                )
                .andExpect(status().isOk())
                .andReturn();
    }

}
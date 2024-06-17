package com.szs.refundinquiryservice.external.service;

import com.szs.refundinquiryservice.common.domain.BusinessException;
import com.szs.refundinquiryservice.external.domain.request.ExternalScrapApiRequest;
import com.szs.refundinquiryservice.external.domain.response.ScrapApiData;
import com.szs.refundinquiryservice.external.domain.response.ExternalScrapApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExternalScrapApiTest {

    @Autowired
    private ExternalScrapApi externalScrapApi;

    @Value("${external.api-key}")
    private String apiKey;

    @Test
    public void testExternalScrapApiExceptionTest() {
        final String name = "동탁";
        final String regNo = "921108-1582816";

        Throwable exception = assertThrows(BusinessException.class, () -> {
            ExternalScrapApiResponse<ScrapApiData> externalScrapApiResponse =
                    externalScrapApi.scrapData("dirty-api-key", new ExternalScrapApiRequest(name, regNo));

        });
        assertEquals("외부 데이터 연동에 실패 하였습니다.", exception.getMessage());
    }

    @Test
    public void testExternalScrapApiTest() {
        final String name = "동탁";
        final String regNo = "921108-1582816";

        ExternalScrapApiResponse<ScrapApiData> externalScrapApiResponse =
                externalScrapApi.scrapData(apiKey, new ExternalScrapApiRequest(name, regNo));

        assertNotNull(externalScrapApiResponse);
        assertTrue(externalScrapApiResponse.isSuccess());
        assertNotNull(externalScrapApiResponse.getData());
        assertEquals(BigDecimal.valueOf(20000000L), externalScrapApiResponse.getData().getComprehensiveIncomeAmount());
        assertEquals(name, externalScrapApiResponse.getData().getName());
        assertEquals("300,000", externalScrapApiResponse.getData().getIncomeDeduction().getTaxDeductionAmount());

    }

}
package com.szs.refundinquiryservice.external.service;

import com.szs.refundinquiryservice.common.domain.ApiConstants;
import com.szs.refundinquiryservice.external.config.FeignConfiguration;
import com.szs.refundinquiryservice.external.domain.request.ExternalScrapApiRequest;
import com.szs.refundinquiryservice.external.domain.response.ExternalScrapApiResponse;
import com.szs.refundinquiryservice.external.domain.response.ScrapApiData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@FeignClient(name = "EXTERNAL-SCRAP-API",
        url = "${external.scrap-api-host}",
        configuration = FeignConfiguration.class)
public interface ExternalScrapApi {

    @PostMapping("/scrap")
    ExternalScrapApiResponse<ScrapApiData> scrapData(
            @RequestHeader(value = ApiConstants.SCRAP_API_KEY_HEADER_NAME) String apiKey,
            @RequestBody ExternalScrapApiRequest externalScrapApiRequest
    );

}

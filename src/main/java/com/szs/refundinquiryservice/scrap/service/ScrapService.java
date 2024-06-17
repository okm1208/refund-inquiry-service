package com.szs.refundinquiryservice.scrap.service;

import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import com.szs.refundinquiryservice.external.domain.request.ExternalScrapApiRequest;
import com.szs.refundinquiryservice.external.domain.response.ExternalScrapApiResponse;
import com.szs.refundinquiryservice.external.domain.response.ScrapApiData;
import com.szs.refundinquiryservice.external.domain.response.ScrapApiError;
import com.szs.refundinquiryservice.external.service.ExternalScrapApi;
import com.szs.refundinquiryservice.scrap.domain.ScrapRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapService {

    private final ExternalScrapApi externalScrapApi;

    private final ScrapStoreService scrapStoreService;

    @Value("${external.api-key}")
    private String apiKey;

    public void scrapInfo(final ScrapRequest scrapRequest) {
        final ExternalScrapApiResponse<ScrapApiData> externalScrapApiResponse = externalScrapApi.scrapData(apiKey, new ExternalScrapApiRequest(
                scrapRequest.getName(),
                scrapRequest.getRegNo()
        ));

        if (!externalScrapApiResponse.isSuccess()) {
            final ScrapApiError scrapApiError = externalScrapApiResponse.getErrors();
            throw new BusinessException(ApiResponseCode.EXTERNAL_SERVER_ERROR,
                    scrapApiError != null ? scrapApiError.getMessage() : ApiResponseCode.EXTERNAL_SERVER_ERROR.getDefaultMessage());
        }

        scrapStoreService.saveScrapInfo(scrapRequest.getRegNo(), externalScrapApiResponse.getData());
    }
}

package com.szs.refundinquiryservice.external.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
public class ExternalScrapErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new ErrorDecoder.Default();

    private final ObjectMapper objectMapper;

    public ExternalScrapErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(final String methodKey, final Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        Exception exception = errorDecoder.decode(methodKey, response);

        if (exception instanceof FeignException) {
            if (responseStatus.is4xxClientError()) {
                return new BusinessException(ApiResponseCode.BAD_REQUEST);
            } else if (responseStatus.is5xxServerError()) {
                return new BusinessException(ApiResponseCode.EXTERNAL_SERVER_ERROR);
            }
        }
        return new BusinessException(ApiResponseCode.ERROR);
    }

}

package com.szs.refundinquiryservice.external.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
public class FeignConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public Request.Options requestOptions() {
        long connectionTimeout = 5;
        long readTimeout = 30;
        return new Request.Options(connectionTimeout, TimeUnit.SECONDS, readTimeout, TimeUnit.SECONDS, false);
    }

    @Bean
    public Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ExternalScrapErrorDecoder(objectMapper);
    }

    @Bean
    Logger.Level logLevel() {
        return Logger.Level.FULL;
    }
}

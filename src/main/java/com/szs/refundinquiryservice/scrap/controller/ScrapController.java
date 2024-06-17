package com.szs.refundinquiryservice.scrap.controller;

import com.szs.refundinquiryservice.common.handler.interceptor.SecureRole;
import com.szs.refundinquiryservice.scrap.domain.ScrapRequest;
import com.szs.refundinquiryservice.scrap.service.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : km.oh
 * @date : 6/15/24
 */

@RestController
@RequestMapping("/szs")
@Tag(name = "Scrap", description = "스크래핑 API")
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;

    @SecureRole
    @PostMapping("/scrap")
    @Operation(summary = "스크래핑", description = "사용자의 소득정보를 외부 사이트에서 스크래핑 한다.")
    public ResponseEntity<Void> scrap(
            @RequestBody ScrapRequest scrapRequest
    ) {
        scrapService.scrapInfo(scrapRequest);
        return ResponseEntity.ok(null);
    }

}

package com.szs.refundinquiryservice.refund.controller;

import com.szs.refundinquiryservice.auth.service.SessionScope;
import com.szs.refundinquiryservice.common.domain.ApiConstants;
import com.szs.refundinquiryservice.common.handler.interceptor.SecureRole;
import com.szs.refundinquiryservice.refund.domain.GetRefundResponse;
import com.szs.refundinquiryservice.refund.service.RefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@RestController
@RequestMapping("/szs")
@Tag(name = "Refund", description = "결정세액 API")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @SecureRole
    @GetMapping("/refund")
    @Operation(summary = "결정세액", description = "사용자의 결정세액을 조회한다.")
    public ResponseEntity<GetRefundResponse> getRefund(
            @Parameter(hidden = true) @RequestHeader(name = ApiConstants.ACCESS_TOKEN_HEADER_NAME, required = false) String accessToken) {
        final Integer userId = SessionScope.getUserIdFromSession();

        return ResponseEntity.ok(new GetRefundResponse(
                refundService.getRefundAmountByUser(userId)
        ));
    }

}

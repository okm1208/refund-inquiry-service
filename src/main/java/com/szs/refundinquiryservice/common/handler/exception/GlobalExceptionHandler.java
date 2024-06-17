package com.szs.refundinquiryservice.common.handler.exception;

import com.szs.refundinquiryservice.common.domain.ApiResponse;
import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author : km.oh
 * @date : 6/15/24
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.failed(ApiResponseCode.BAD_REQUEST, e.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> exceptionHandler(Exception e, HttpServletRequest request) {

        if (e instanceof BusinessException) {
            log.error("api exception : {}", e);
            BusinessException businessException = (BusinessException) e;
            return ResponseEntity.status(businessException.getHttpStatus()).body(
                    ApiResponse.failed(businessException.getCode(), businessException.getMessage())
            );
        } else {
            log.error("internal server exception : {}", e.getMessage());
            log.error("{}", ExceptionUtils.getStackTrace(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.failed(ApiResponseCode.ERROR)
            );
        }
    }

}

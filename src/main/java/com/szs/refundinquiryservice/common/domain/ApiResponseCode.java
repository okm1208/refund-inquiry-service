package com.szs.refundinquiryservice.common.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Getter
public enum ApiResponseCode {
    SUCCESS(0, HttpStatus.OK, null),
    BAD_REQUEST(1, HttpStatus.BAD_REQUEST, ""),
    ILLEGAL_STATE(2, HttpStatus.BAD_REQUEST, "common.error.illegal_state"),
    UNAUTHENTICATED(3, HttpStatus.UNAUTHORIZED, null),
    UNAUTHORIZED(4, HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NOT_FOUND(5, HttpStatus.NOT_FOUND, null),
    CONFLICT(6, HttpStatus.CONFLICT, null),
    EXPIRED_ACCESS_TOKEN(7, HttpStatus.UNAUTHORIZED, "access-token 정보가 유효하지 않거나 만료되었습니다."),
    METHOD_NOT_ALLOWED(8, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 메서드입니다."),
    TIMEOUT(900, HttpStatus.REQUEST_TIMEOUT, null),
    EXTERNAL_SERVER_ERROR(901, HttpStatus.INTERNAL_SERVER_ERROR, "외부 데이터 연동에 실패 하였습니다."),
    ERROR(999, HttpStatus.INTERNAL_SERVER_ERROR, "예상하지 못한 오류가 발생했습니다. 서비스에 다시 접속해주세요.");

    private final int id;

    private final HttpStatus httpStatus;

    private final String defaultMessage;

    ApiResponseCode(final int id, final String defaultMessage) {
        this(id, HttpStatus.BAD_REQUEST, defaultMessage);
    }

    ApiResponseCode(final int id, final HttpStatus httpStatus, final String defaultMessage) {
        this.id = id;
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}

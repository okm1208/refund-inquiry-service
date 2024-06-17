package com.szs.refundinquiryservice.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author : km.oh
 * @since : 6/15/24
 **/
@Getter
@Setter
public class ApiResponse<T> {

    private int code = ApiResponseCode.SUCCESS.getId();

    private String message;

    private T data;

    @JsonIgnore
    private HttpStatus httpStatus;

    public static <T> ApiResponse<T> success() {
        return with((T) null).code(ApiResponseCode.SUCCESS).message(null).httpStatus(HttpStatus.OK);
    }

    public static <T> ApiResponse<T> success(T data) {
        return with(data).code(ApiResponseCode.SUCCESS).message(null).httpStatus(HttpStatus.OK);
    }

    private static <T> ApiResponse<T> with(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.data = data;

        return response;
    }

    public ApiResponse<T> code(@NotNull ApiResponseCode code) {
        this.code = code.getId();
        return this;
    }

    public ApiResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse<T> message(String message) {
        this.message = message;
        return this;
    }

    public ApiResponse<T> httpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public static <T> ApiResponse<T> failed(ApiResponseCode errorCode) {
        return failed(errorCode, errorCode.getDefaultMessage());
    }
    public static <T> ApiResponse<T> failed(ApiResponseCode errorCode, String message) {
        ApiResponse<T> response = new ApiResponse<>();

        response.code = errorCode.getId();
        response.message = message;
        response.httpStatus = errorCode.getHttpStatus();

        return response;
    }

}


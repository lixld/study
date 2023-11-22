package com.lixl.study.beautyController.exception.business;

import com.lixl.study.beautyController.exception.common.ErrorCode;
import lombok.Getter;

@Getter
public enum ApiExceptionEnum implements ErrorCode {
    APPLICATION_ERROR(2000,"业务异常");

    private int code;
    private String message;

    ApiExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return message;
    }
}

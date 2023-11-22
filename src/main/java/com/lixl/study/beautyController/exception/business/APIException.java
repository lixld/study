package com.lixl.study.beautyController.exception.business;

import com.lixl.study.beautyController.exception.common.ErrorCode;
import lombok.Data;

@Data
public class APIException extends RuntimeException {
    private int code;
    private String message;

    public APIException(ErrorCode statusCode, String message) {
        super(message);
        this.code = statusCode.getCode();
        this.message =  statusCode.getMsg();
    }

    public APIException(String message) {
        super(message);
        this.code = ApiExceptionEnum.APPLICATION_ERROR.getCode();
        this.message = ApiExceptionEnum.APPLICATION_ERROR.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

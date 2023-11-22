//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lixl.study.beautyController.exception.common;



public enum ErrorCodeEum implements ErrorCode {
    SUCCESS(0, "success"),
    FAIL(1, "fail"),
    PARAM_ERROR(4, "param error"),
    TOKEN_ERROR(401, "Unauthorized"),
    ACCESS_DENY(403, "Forbidden");

    private int code;
    private String msg;

    private ErrorCodeEum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}

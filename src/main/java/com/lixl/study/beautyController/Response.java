//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lixl.study.beautyController;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lixl.study.beautyController.exception.common.ErrorCode;
import com.lixl.study.beautyController.exception.common.ErrorCodeEum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Response<T> implements Serializable {
    public static final ErrorCodeEum SUCCESS;
    public static final ErrorCodeEum FAIL;

    private int code;

    private String msg;

    private T data;

    private Map<String, Object> extMap;


    public static <T> Response<T> succ() {
        Response<T> response = (Response<T>) builder().code(SUCCESS.getCode()).msg(SUCCESS.getMsg()).build();
        return response;
    }

    public static <T> Response<T> succ(T data) {
        Response<T> response = (Response<T>) builder().code(SUCCESS.getCode()).msg(SUCCESS.getMsg()).data(data).build();
        return response;
    }

    public static <T> Response<T> fail() {
        Response<T> response = (Response<T>) builder().code(ErrorCodeEum.FAIL.getCode()).msg(ErrorCodeEum.FAIL.getMsg()).build();
        return response;
    }

    public static <T> Response<T> fail(String msg) {
        Response<T> response = (Response<T>) builder().code(ErrorCodeEum.FAIL.getCode()).msg(msg).build();
        return response;
    }

    public static <T> Response<T> fail(String msg, Map<String, Object> extMap) {
        return (Response<T>) builder().code(ErrorCodeEum.FAIL.getCode()).msg(msg).extMap(extMap).build();
    }

    public static <T> Response<T> fail(int code, String msg) {
        return (Response<T>) builder().code(code).msg(msg).build();
    }

    public static <T> Response<T> fail(ErrorCode errorCode) {
        return (Response<T>) builder().code(errorCode.getCode()).msg(errorCode.getMsg()).build();
    }

    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESS.getCode() == this.code;
    }

    public Response addExtInfo(String key, Object value) {
        if (Objects.isNull(this.extMap)) {
            this.extMap = new HashMap();
        }

        this.extMap.put(key, value);
        return this;
    }

    public static <T> Response.ResponseBuilder<T> builder() {
        return new Response.ResponseBuilder();
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public Map<String, Object> getExtMap() {
        return this.extMap;
    }



    public Response<T> setCode(final int code) {
        this.code = code;
        return this;
    }

    public Response<T> setMsg(final String msg) {
        this.msg = msg;
        return this;
    }

    public Response<T> setData(final T data) {
        this.data = data;
        return this;
    }

    public Response<T> setExtMap(final Map<String, Object> extMap) {
        this.extMap = extMap;
        return this;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Response;
    }


    public String toString() {
        return "Response(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ", extMap=" + this.getExtMap() + ", traceId=" + ")";
    }

    public Response(final int code, final String msg, final T data, final Map<String, Object> extMap, final String traceId) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.extMap = extMap;
    }

    public Response() {
    }

    static {
        SUCCESS = ErrorCodeEum.SUCCESS;
        FAIL = ErrorCodeEum.FAIL;
    }

    public static class ResponseBuilder<T> {
        private int code;
        private String msg;
        private T data;
        private Map<String, Object> extMap;
        private String traceId;

        ResponseBuilder() {
        }

        public Response.ResponseBuilder<T> code(final int code) {
            this.code = code;
            return this;
        }

        public Response.ResponseBuilder<T> msg(final String msg) {
            this.msg = msg;
            return this;
        }

        public Response.ResponseBuilder<T> data(final T data) {
            this.data = data;
            return this;
        }

        public Response.ResponseBuilder<T> extMap(final Map<String, Object> extMap) {
            this.extMap = extMap;
            return this;
        }



        public Response<T> build() {
            return new Response(this.code, this.msg, this.data, this.extMap, this.traceId);
        }

        public String toString() {
            return "Response.ResponseBuilder(code=" + this.code + ", msg=" + this.msg + ", data=" + this.data + ", extMap=" + this.extMap + ", traceId=" + this.traceId + ")";
        }
    }
}

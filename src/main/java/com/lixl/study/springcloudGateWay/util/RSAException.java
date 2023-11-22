package com.lixl.study.springcloudGateWay.util;

import lombok.Getter;

@Getter
public class RSAException extends RuntimeException {

    private final String message;

    public RSAException(String message) {
        this.message = message;
    }

}
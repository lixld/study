package com.lixl.study.RPC_diy.clientSide;

public enum State {

    SUCCESS(200,"success"),
    ERROR(500,"error"),
    NOTFOUND(404,"not found");
    private int code;
    private String message;

    State(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

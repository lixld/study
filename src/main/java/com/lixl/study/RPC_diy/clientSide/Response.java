package com.lixl.study.RPC_diy.clientSide;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author : lixl
 * @date : 2021-01-13 21:18:14
 **/
@Data
public class Response implements Serializable {
    private State state;
    private Map<String,String> headers;
    private Class returnType;
    private Object returnValue;
    private Exception exception;

    public Response(State state) {
        this.state = state;
    }
}

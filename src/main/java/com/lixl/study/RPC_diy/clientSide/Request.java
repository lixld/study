package com.lixl.study.RPC_diy.clientSide;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author : lixl
 * @date : 2021-01-13 21:15:35
 **/
@Data
public class Request implements Serializable {
    private String serverName;
    private String method;
    private Map<String,String> headers;
    private Class[] parameterTypes;
    private Object[] parameters;


}

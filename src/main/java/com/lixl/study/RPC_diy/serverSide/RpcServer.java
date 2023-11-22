package com.lixl.study.RPC_diy.serverSide;

/**
 * @author : lixl
 * @date : 2021-01-14 10:33:08
 **/
public abstract class RpcServer {
    int port;
    String protocol;
    RequestHandler handler;
    abstract void start();
    abstract void stop();

    public RpcServer(int port, String protocol, RequestHandler handler) {
        this.port = port;
        this.protocol = protocol;
        this.handler = handler;
    }
}

package com.lixl.study.RPC_diy.clientSide;

/**
 * @author : lixl
 * @date : 2021-01-13 21:31:41
 **/
public interface NetClient {
    byte[] sendRequest(byte[] data, ServiceInfo serverInfo);
}

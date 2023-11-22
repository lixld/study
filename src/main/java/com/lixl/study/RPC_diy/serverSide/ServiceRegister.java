package com.lixl.study.RPC_diy.serverSide;

public interface ServiceRegister {
    void register(ServiceObject serviceObject,String protocol,int port);
    ServiceObject getServiceObject(String name);

}

package com.lixl.study.RPC_diy.serverSide;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : lixl
 * @date : 2021-01-14 10:13:22
 **/
@Data
public class DefaultServiceRegister implements ServiceRegister {
    private Map<String,ServiceObject> serviceObjectMap = new HashMap<>();
    @Override
    public void register(ServiceObject serviceObject, String protocol, int port) {
        if (serviceObject == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        this.serviceObjectMap.put(serviceObject.getInterf().getName()/*.getInterfName()*/,serviceObject);
    }

    @Override
    public ServiceObject getServiceObject(String name) {
        return this.serviceObjectMap.get(name);
    }
}

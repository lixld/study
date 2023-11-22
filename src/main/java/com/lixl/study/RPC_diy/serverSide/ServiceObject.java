package com.lixl.study.RPC_diy.serverSide;

import lombok.Data;

/**
 * 服务对象类
 * @author : lixl
 * @date : 2021-01-14 10:06:24
 **/

@Data
public class ServiceObject {
//    String interfName;//接口类名称
    String address;//ip:port
    Class<?> interf;//接口类
    Object interfImpl;//接口实现类

    public ServiceObject(/*String interfName, */Class<?> interf, Object interfImpl) {
//        this.interfName = interfName;
        this.interf = interf;
        this.interfImpl = interfImpl;
    }
}

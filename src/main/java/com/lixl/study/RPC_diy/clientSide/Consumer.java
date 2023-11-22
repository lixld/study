package com.lixl.study.RPC_diy.clientSide;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : lixl
 * @date : 2021-01-13 22:15:21
 **/
public class Consumer {
    public static void main(String[] args) {
        ClientStubProxyFactory clientStubProxyFactory = new ClientStubProxyFactory();
        clientStubProxyFactory.setSid(new ZoookeeperServiceInfoDiscoverer());
        clientStubProxyFactory.setNetClient(new NettyNetClient());
        Map<String, MessageProtocol> messageProtocolMap = new HashMap<>();
        messageProtocolMap.put("javas",new JavaSerializeMessageProtocal());
        clientStubProxyFactory.setSupportProtocols(messageProtocolMap);

        DemoServiceImpl proxy = clientStubProxyFactory.getProxy(DemoServiceImpl.class);

        String hello = proxy.sayHello(" clever!");
        System.out.println(hello);

        Point multipont = proxy.multipont(new Point(1, 2));
        System.out.println(multipont.toString());
    }
}

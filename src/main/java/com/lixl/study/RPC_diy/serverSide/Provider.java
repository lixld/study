package com.lixl.study.RPC_diy.serverSide;

import com.lixl.study.RPC_diy.clientSide.DemoService;
import com.lixl.study.RPC_diy.clientSide.DemoServiceImpl;
import com.lixl.study.RPC_diy.clientSide.JavaSerializeMessageProtocal;

import java.io.IOException;

/**
 * @author : lixl
 * @date : 2021-01-14 10:03:34
 **/
public class Provider {
    public static void main(String[] args) {
        int port = 19000;//rpc.port
        String protocol = "javas";//指明--序列化协议rpc.protocol

        //准备好服务
        DemoService demoServiceImpl = new DemoServiceImpl();
        ServiceObject serviceObject = new ServiceObject(/*DemoService.class.getName(),*/DemoService.class,demoServiceImpl);

        //服务注册
        ServiceRegister serviceRegister = new ZkExportServiceRegister();
        serviceRegister.register(serviceObject,protocol,port);

        RequestHandler requestHandler = new RequestHandler(new JavaSerializeMessageProtocal(), serviceRegister);

        RpcServer rpcServer = new NettyRpcService(port, protocol, requestHandler);

        rpcServer.start();
        try {
            System.in.read();//按任意键退出
        } catch (IOException e) {
            e.printStackTrace();
        }

        rpcServer.stop();

    }
}

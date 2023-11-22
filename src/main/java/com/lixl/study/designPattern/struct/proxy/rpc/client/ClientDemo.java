package com.lixl.study.designPattern.struct.proxy.rpc.client;

import com.lixl.study.designPattern.struct.proxy.rpc.RpcClient;

public class ClientDemo {

    public static void main(String[] args) throws Exception {
      RpcClient client = new RpcClient();
      CalculatorService service = client.refer(CalculatorService.class, "127.0.0.1", 1234);
      int result = service.add(2, 4);
      System.out.println("result:" + result);
    }

}

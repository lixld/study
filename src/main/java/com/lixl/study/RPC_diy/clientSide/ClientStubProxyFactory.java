package com.lixl.study.RPC_diy.clientSide;


import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author : lixl
 * @date : 2021-01-13 20:56:31
 **/
@Data
public class ClientStubProxyFactory {

    private ServiceInfoDiscover sid;
    private Map<String, MessageProtocol> supportProtocols;
    private NetClient netClient;
    private Map<Class<?>, Object> objectCache = new HashMap<>();


    /**
     * 客户端需要调用的远程代理类
     * @param interf --注意，这里是接口
     * @param <T>
     * @return
     */
    public <T> T getProxy(Class<T> interf) {
        Object o = objectCache.get(interf);
        if (o == null) {
            o = Proxy.newProxyInstance(interf.getClassLoader(), new Class<?>[]{interf}, new ClientInvocationHandler(interf));
            this.objectCache.put(interf, o);
        }
        return null;
    }

    private class ClientInvocationHandler implements InvocationHandler {
        private Class interf;//TODO 接口

        public <T> ClientInvocationHandler(Class<T> interf) {
            this.interf = interf;
        }

        private Random random = new Random();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("toString")) {
                return proxy.getClass().toGenericString();
            }
            if (method.getName().equals("hashCode")) {
                return 0;
            }
            //获取服务信息
            String serverName = this.interf.getName();
            //获取服务提供者
            List<ServiceInfo> serverInfos = sid.getServerInfos(serverName);
            int i = random.nextInt(serverInfos.size());
            //随机选择一个服务提供者（软负载）
            ServiceInfo serverInfo = serverInfos.get(i);

            String address = serverInfo.getAddress();

            //构造request对象
            Request request = new Request();
            request.setParameters(args);
            request.setServerName(serverName);
            request.setMethod(method.getName());
            request.setParameterTypes(method.getParameterTypes());

            //序列化协议--编组
            String protocol = serverInfo.getProtocol();
            MessageProtocol messageProtocol = supportProtocols.get(protocol);
            byte[] data = messageProtocol.marshallingRequest(request);

            //传输协议--netClient调用
            byte[] reponseData = netClient.sendRequest(data, serverInfo);

            //解组
            Response response = messageProtocol.unMarshllingReponse(reponseData);


            Exception exception = response.getException();
            if (exception != null) {
                throw exception;
            }
            Object returnValue = response.getReturnValue();

            return returnValue;
        }
    }
}

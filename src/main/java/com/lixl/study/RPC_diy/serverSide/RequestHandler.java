package com.lixl.study.RPC_diy.serverSide;

import com.lixl.study.RPC_diy.clientSide.MessageProtocol;
import com.lixl.study.RPC_diy.clientSide.Request;
import com.lixl.study.RPC_diy.clientSide.Response;
import com.lixl.study.RPC_diy.clientSide.State;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author : lixl
 * @date : 2021-01-14 10:34:10
 **/
@Data
public class RequestHandler {
    private MessageProtocol protocol;
    private ServiceRegister serviceRegister;

    public RequestHandler(MessageProtocol protocol, ServiceRegister serviceRegister) {
        this.protocol = protocol;
        this.serviceRegister = serviceRegister;
    }

    public byte[] handlerRequest(byte[] data) {
        //先解组byte[]数组请求为Request对象
        Request request = this.protocol.unMarshallingRequest(data);

        //先去取值
        ServiceObject serviceObject = serviceRegister.getServiceObject(request.getServerName());

        Response response;
        if (serviceObject == null) {
            response = new Response(State.NOTFOUND);
        }else {
            //反射调用该服务对象的方法
            String method = request.getMethod();
            Object[] parameters = request.getParameters();
            Class[] parameterTypes = request.getParameterTypes();
            try {
                Method method1 = serviceObject.getInterf().getMethod(method, parameterTypes);
                Object returnValue = method1.invoke(serviceObject.getInterfImpl(), parameters);
                response = new Response(State.SUCCESS);
                response.setReturnValue(returnValue);
            } catch (NoSuchMethodException |IllegalAccessException|InvocationTargetException e) {
                e.printStackTrace();
                response = new Response(State.ERROR);
                response.setException(e);
            }
        }


        //再把执行结果Response对象编组为byte[]数组数据，用来做网络传输（因为网络传输的基本单位是byte数组）
        byte[] bytes = protocol.marshallingResponse(response);


        return bytes;
    }
}

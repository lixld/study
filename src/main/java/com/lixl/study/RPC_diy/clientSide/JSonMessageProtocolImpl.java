package com.lixl.study.RPC_diy.clientSide;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @author : lixl
 * @date : 2021-01-13 21:47:14
 **/
public class JSonMessageProtocolImpl implements MessageProtocol {
    @Override
    public byte[] marshallingRequest(Request request) {
        Request temp = new Request();
        String serverName = request.getServerName();temp.setServerName(serverName);
        String method = request.getMethod();temp.setMethod(method);
        Map<String, String> headers = request.getHeaders();temp.setHeaders(headers);
        Class[] parameterTypes = request.getParameterTypes();temp.setParameterTypes(parameterTypes);

        Object[] parameters = request.getParameters();
        if (parameters != null) {
            Object[] serizeParams = new Object[parameters.length];
            //这里有点问题
            for (int i = 0; i < parameters.length; i++) {
                serizeParams[i] = JSON.toJSONString(parameters[i]);
            }
            temp.setParameters(serizeParams);
        }
        byte[] bytes = JSON.toJSONBytes(temp);
        return bytes;
    }

    @Override
    public Request unMarshallingRequest(byte[] request) {
        return null;
    }

    @Override
    public byte[] marshallingResponse(Response response) {
        return new byte[0];
    }

    @Override
    public Response unMarshllingReponse(byte[] reponse) {
        return null;
    }
}

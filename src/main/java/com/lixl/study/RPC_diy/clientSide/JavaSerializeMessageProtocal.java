package com.lixl.study.RPC_diy.clientSide;

import java.io.*;

/**
 * @author : lixl
 * @date : 2021-01-13 21:55:21
 **/
public class JavaSerializeMessageProtocal implements MessageProtocol {

    //整个对象序列化------对参数有个要求，所有的参数都必须是能够序列化的，也就是必须实现serializeable接口
    private byte[] seriallize(Object object){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //整个对象反序列化------对参数有个要求，所有的参数都必须是能够反序列化的，也就是必须实现serializeable接口
    private <T> T unSeriallize(byte[] request,Class<T> t){
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new ByteArrayInputStream(request));
            T o = (T) objectInputStream.readObject();
            return o;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public byte[] marshallingRequest(Request request) {

        return this.seriallize(request);
    }

    @Override
    public Request unMarshallingRequest(byte[] request) {

        Request requestObject = unSeriallize(request, Request.class);
        return requestObject;
    }

    @Override
    public byte[] marshallingResponse(Response response) {
        return this.seriallize(response);
    }

    @Override
    public Response unMarshllingReponse(byte[] responseByte) {
        Response response = this.unSeriallize(responseByte, Response.class);
        return response;
    }
}

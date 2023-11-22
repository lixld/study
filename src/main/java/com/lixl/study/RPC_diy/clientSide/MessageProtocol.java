package com.lixl.study.RPC_diy.clientSide;

/**
 * @author : lixl
 * @date : 2021-01-13 20:58:22
 **/
public interface MessageProtocol {
    byte[] marshallingRequest(Request request);
    Request unMarshallingRequest(byte[] request);

    byte[] marshallingResponse(Response response);
    Response unMarshllingReponse(byte[] reponse);

}

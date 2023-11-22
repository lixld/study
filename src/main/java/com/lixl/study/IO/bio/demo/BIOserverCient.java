package com.lixl.study.IO.bio.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author : lixl
 * @date : 2020-06-05 19:37:22
 **/
public class BIOserverCient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",9999);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello world".getBytes());
        outputStream.close();
        socket.close();
    }
}

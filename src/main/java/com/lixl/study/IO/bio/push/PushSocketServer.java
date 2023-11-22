package com.lixl.study.IO.bio.demo.push;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : lixl
 * @date : 2020-11-26 10:33:15
 **/
public class PushSocketServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            String cache = new String();
            while(true){
                Socket accept = serverSocket.accept();
                InputStream inputStream = accept.getInputStream();
                while (true){
                    byte[] request = new byte[220];
                    int read = inputStream.read(request);
                    if (read == -1){
                        break;
                    }
                    //得到请求内容，解析，得到发送对象和发送内容
                    String content = new String(request);
                    //解析数据包（1，2，3没法解析）消息,然后进行后续处理
                    //content = "#000000000000sdfsdfsdfdsfdsf"
                    //校验这个数据包，根据协议的规定 -----------比如协议规定消息以#开头 以%结束【此时如果用户内容里面有这些内容需要转译，比如XML中的CDATA】

                    /**
                     * 以上的这些内容，Netty做了封装，于是有了这个框架
                     */
                    if (content.getBytes().length>220){

                    }else if (content.getBytes().length<220){//没有%结束
                        cache = cache + content; //缓存起来，去处理
                    }
                    //每次读取的数据不能保证是完整的
                    System.out.println(content);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

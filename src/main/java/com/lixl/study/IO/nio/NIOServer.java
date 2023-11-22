package com.lixl.study.IO.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


//NIO非阻塞io
public class NIOServer {


    public static void main(String[] args) throws IOException {
        {
            //绑定端口
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(8080));
            System.out.println("NIO 启动8080");


            while (true) {
                //获取新连接
                SocketChannel socketChannel = serverSocketChannel.accept();//非阻塞模式,获取新的tcp连接通道，这个方法可能返回null
                if (socketChannel != null) {
                    System.out.println("收到新连接：" + socketChannel.getRemoteAddress());

                    socketChannel.configureBlocking(false);

                    //定义了个缓冲区
                    ByteBuffer requestBuffer = ByteBuffer.allocate(1024);

                    /**
                     * V0版本的问题：如果多个连接，则后面的连接会被前面的连接阻塞，直到前面的连接都读完，其实是人为阻塞了
                     * 读数据
                     * socketChannel.read(requestBuffer) == -1
                     * 表示网络连接已经中断
                     * */
                    while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {//-1表示读取结束了
                        //TODO 其实就是自己在主动探测，到底有没有数据发出了？
                        //长连接情况下需要手动判断数据有没有读取结束（此处做一个简单判断：超过0字节就认为请求结束了）
                        if (requestBuffer.position() > 0) break;//代表有数据写入了
                    }
                    if (requestBuffer.position() == 0) continue;//如果没数据了，则不继续后面的处理
                    requestBuffer.flip();//转换为读模式

                    byte[] content = new byte[requestBuffer.limit()];
                    requestBuffer.get(content);
                    System.out.println(new java.lang.String(content));
                    System.out.println("收到数据来自：" + socketChannel.getRemoteAddress());

                    /**
                     * 写数据
                     */
                    //响应结果 200
                    java.lang.String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length:11\r\n\r\n" +
                            "hello world";
                    ByteBuffer wrap = ByteBuffer.wrap(response.getBytes());
                    while (requestBuffer.hasRemaining()) {
                        socketChannel.write(wrap);//非阻塞
                    }
                }
            }
        }
    }
}

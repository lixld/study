package com.lixl.study.IO.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * 直接基于非阻塞的方法，一个线程处理轮询所有请求
 */
public class NIOServerV1 {

    /**
     * 已经建立连接的请求集合
     */
    private static ArrayList<SocketChannel> channels = new ArrayList<>();
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
                /**
                 *  V1版本的改定：只有有新的连接（而且不为空），则将新的连接加入到内存数组中
                 *  然后遍历数组，来分别读取每个channel的内容
                 *  问题在于，用while循环依然低效，且不够优雅
                 */
                if (socketChannel != null) {
                    System.out.println("收到新连接：" + socketChannel.getRemoteAddress());

                    socketChannel.configureBlocking(false);
                    channels.add(socketChannel);

                } else {
                    //没有新连接的情况下，就去处理现有连接的数据，处理完就删除掉
                    Iterator<SocketChannel> iterator = channels.iterator();
                    while (iterator.hasNext()) {
                        SocketChannel ch = iterator.next();


                        //定义了个缓冲区
                        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);

                        if (ch.read(requestBuffer) == 0) {
                            //等于0 ，代表这个通道没有数据需要处理，那就待会再处理
                            continue;
                        }

                        while (ch.isOpen() && ch.read(requestBuffer) != -1) {//-1表示读取结束了
                            //长连接情况下需要手动判断数据有没有读取结束（此处做一个简单判断：超过0字节就认为请求结束了）
                            if (requestBuffer.position() > 0) break;//代表有数据写入了d
                        }
                        if (requestBuffer.position() == 0) continue;//如果没数据了，则不继续后面的处理
                        requestBuffer.flip();

                        byte[] content = new byte[requestBuffer.limit()];
                        requestBuffer.get(content);
                        System.out.println(new String(content));
                        System.out.println("收到数据来自：" + ch.getRemoteAddress());

                        //响应结果 200
                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Length:11\r\n\r\n" +
                                "hello world";
                        ByteBuffer wrap = ByteBuffer.wrap(response.getBytes());
                        while (requestBuffer.hasRemaining()) {
                            ch.write(wrap);
                        }
                    }
                }
            }
        }
    }
}

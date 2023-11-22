package com.lixl.study.IO.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author : lixl
 * @date : 2020-06-06 10:09:31
 **/
public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

        //调用了connect不一定就是连接上了，还需要再判定一下
        while (!socketChannel.finishConnect()) {
            //没连接上，则一直等待
            Thread.yield();
        }


        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入：");
        //发送内容
        String msg = scanner.nextLine();
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
        //发送消息给服务端
        while (byteBuffer.hasRemaining()) {
            socketChannel.write(byteBuffer);
        }


        //接收来自服务端的消息，用一个buffer来装这些数据
        System.out.println("收到服务器响应");
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
            //长连接情况下，需要手动判断有没有读取结束
            if (requestBuffer.position() > 0) break;
        }
        requestBuffer.flip();
        byte[] content = new byte[requestBuffer.limit()];
        requestBuffer.get(content);
        System.out.println(new String(content));
        scanner.close();
        socketChannel.close();
    }
}

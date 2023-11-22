package com.lixl.study.IO.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


/**
 * 结合selector实现的非阻塞服务端（放弃对channel的轮询，借助消息通知机制）
 */
public class NIOServerV2 {

    /**
     * 已经建立连接的请求集合
     */
    private static ArrayList<SocketChannel> channels = new ArrayList<>();

    public static void main(String[] args) throws IOException {

            //1.创建网络服务端ServerSocketChannel
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            //2.构建一个selector选择器，并且将channel注册上去
            Selector selector = Selector.open();
            SelectionKey selectionKey = serverSocketChannel.register(selector, 0, serverSocketChannel);//将serverSocketChannel注册到selector
            selectionKey.interestOps(SelectionKey.OP_ACCEPT);//对serverSocketChannel上的accept事件感兴趣（serverSocketChannel只能支持accept操作）

            //3.绑定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(8080));
            System.out.println("启动成功");
            while (true) {
                //不再去轮询通道，改用下面轮询事件的方式。
                /**
                 * select方法有阻塞效果。直到有事件通知才会返回
                 * 可以设置超时时间
                 */
                selector.select();
                //获取事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //遍历查询结果
                Iterator<SelectionKey> iterator1 = selectionKeys.iterator();
                while (iterator1.hasNext()) {
                    //被封装的查询结果---SelectionKey
                    SelectionKey selectKey = iterator1.next();
                    iterator1.remove();

                    try {
                        //关注Read和Accept两个事件
                        if (selectKey.isAcceptable()) {
                            ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectKey.attachment();

                            //将拿到的客户端连接通道，注册到seletor上
                            SocketChannel clientSocketChannel = serverSocketChannel1.accept();
                            clientSocketChannel.configureBlocking(false);
                            clientSocketChannel.register(selector, SelectionKey.OP_READ, clientSocketChannel);
                            System.out.println("收到新连接：" + clientSocketChannel.getRemoteAddress());
                        }
                        //客户端已经连接好，可传数据了
                        if (selectKey.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) selectKey.attachment();
                            ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                            while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
                                if (requestBuffer.position() > 0) break;
                            }
                            if (requestBuffer.position() == 0) continue;//如果没数据了，则不继续后面的处理
                            requestBuffer.flip();
                            byte[] content = new byte[requestBuffer.limit()];
                            requestBuffer.get(content);
                            System.out.println(new String(content));
                            System.out.println("收到数据来自：" + socketChannel.getRemoteAddress());
                            //响应结果 200
                            String response = "HTTP/1.1 200 OK\r\n" +
                                    "Content-Length:11\r\n\r\n" +
                                    "hello world";
                            ByteBuffer wrap = ByteBuffer.wrap(response.getBytes());
                            while (requestBuffer.hasRemaining()) {
                                socketChannel.write(wrap);
                            }
                        }
                    } catch (IOException e) {
                        //取消事件订阅
                        selectKey.cancel();
                        e.printStackTrace();
                    }
                }
                selector.selectNow();
            }
        //问题1：此处一个selector监听所有事件，一个线程处理所有请求事件，会成为瓶颈！要有多线程的运用
    }
}

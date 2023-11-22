package com.lixl.study.IO.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 * reactor线程模型
 *
 * NIO selector 多路复用reactor线程模型
 */
public class NIOServerV3 {

    /**
     * 处理业务操作的线程，具体的耗时的任务
     */
    private static ExecutorService workPool = Executors.newCachedThreadPool();

    /**
     * 抽象类
     * 封装了selector.select()等事件轮询的代码
     *
     * 在netty里面叫eventLoop
     */
    abstract class ReactorThread extends Thread {
        Selector selector;
        LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        volatile boolean running = true;

        private ReactorThread() throws IOException {
            selector = Selector.open();
        }

        /**
         * Selector监听到有事件后，调用这个方法
         * accept线程实现和I/O线程的实现不同，会在重载它的实现
         *
         * @param channel
         * @throws Exception
         */
        public abstract void handler(SelectableChannel channel) throws Exception;

        @Override
        public void run() {
            Runnable task;
            while (running) {
                //这就是多线程，放任务进去
                while ((task = taskQueue.poll()) != null) {
                    task.run();
                }
                /**
                 * select方法有阻塞效果。直到有事件通知才会返回
                 * 可以设置超时时间
                 */
                try {
                    selector.select(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    int readyOps = key.readyOps();
                    //事件来源不同--事件也不--下面的read和accept是服务端通道 [serverSocketChannel]
                    if ((readyOps & (SelectionKey.OP_READ | SelectionKey.OP_ACCEPT)) != 0 || readyOps == 0) {
                        try {
                            SelectableChannel channel = (SelectableChannel) key.attachment();
                            channel.configureBlocking(false);
                            //抽象方法
                            handler(channel);
                            if (!channel.isOpen()) {
                                key.cancel();//如果关闭了，就取消这个key的订阅
                            }
                        } catch (Exception e) {
                            key.cancel();//如果异常，就取消这个key的订阅
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

        public SelectionKey register(SelectableChannel selectableChannel) throws IOException {
//            selectableChannel.configureBlocking(false);
//            serverSocketChannel.configureBlocking(false);下面的调用程序里已经写了
            SelectionKey selectionKey = selectableChannel.register(selector, 0, selectableChannel);
            if (selectableChannel instanceof SocketChannel){
                SocketChannel clientSocketChannel = (SocketChannel) selectableChannel;
                System.out.println("收到新连接：" + clientSocketChannel.getRemoteAddress());
            }
            if (selectableChannel instanceof ServerSocketChannel){
                System.out.println("服务端通道注册：serverSocketChannel");
            }
//            selectionKey.interestOps(SelectionKey.OP_ACCEPT); 下面的调用程序里已经写了
            return selectionKey;
        }

        protected void doStart() throws IOException {
//            selector.selectNow();
            this.start();
        }

    }

    private ServerSocketChannel serverSocketChannel;



    //1.创建多个线程-accept处理  reactor线程（accept线程）
    private ReactorThread[] mainReactorThreads = new ReactorThread[1];



    //2.创建多个线程-iot处理     reactor线程（I/O线程）
    private ReactorThread[] subReactorThreads = new ReactorThread[8];


    /**
     * 初始化线程组
     *
     * @throws IOException
     */
    private void newGroup() throws IOException {
        //创建IO线程，负责处理 客户端连接以后的 SocketChannel的IO读写 ---------读取客户端---->服务器的消息
        for (int i = 0; i < subReactorThreads.length; i++) {
            /**
             * 这里就是创建多线程
             */

            subReactorThreads[i] = new ReactorThread() {
                //重载handler方法--处理客户端通道
                //当有任务被提交的时候才被执行
                @Override
                public void handler(SelectableChannel channel) throws Exception {
                    //work线程只负责处理IO，不处理accept事件
                    SocketChannel ch = (SocketChannel) channel;
                    ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                    while (ch.isOpen() && ch.read(requestBuffer) != -1) {
                        //长连接情况下，需要手动判断数据有没有读取结束（此处做一个简单判断，超过0字节就让认为请求结束了）
                        if (requestBuffer.position() > 0) break;
                    }
                    if (requestBuffer.position() == 0) return;//如果没数据了不做后面的处理
                    requestBuffer.flip();
                    byte[] content = new byte[requestBuffer.limit()];
                    requestBuffer.get(content);
                    System.out.println(new String(content));
                    System.out.println(Thread.currentThread().getName() + "收到数据，来自" + ch.getRemoteAddress());
                    //TODO 业务处理 数据库、接口
                    workPool.submit(() -> {

                    });

                    //响应结果 200
                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length:11\r\n\r\n" +
                            "hello world";
                    ByteBuffer wrap = ByteBuffer.wrap(response.getBytes());
                    while (requestBuffer.hasRemaining()) {
                        ch.write(wrap);
                    }
                }
            };
        }


        //创建mainReactor线程，只负责处理serverSocketChannel
        for (int i = 0; i < mainReactorThreads.length; i++) {
            //新建多个线程
            mainReactorThreads[i] = new ReactorThread() {
                AtomicInteger incr = new AtomicInteger(0);
                //重载handler方法--处理服务端通道
                @Override
                public void handler(SelectableChannel channel) throws Exception {
                    //只做请求分发，不做具体的数据读取
                    ServerSocketChannel ch = (ServerSocketChannel) channel;
                    SocketChannel socketChannel = ch.accept();//客户端
                    socketChannel.configureBlocking(false);

                    //收到连接建立的通知之后，分发给IO线程去读取数据（客户端通道内容）
                    int index = incr.getAndIncrement() % subReactorThreads.length;
                    ReactorThread reactorThread = subReactorThreads[index];//从I/O线程组中选取其中一个线程，
                    reactorThread.doStart();//线程启动

                    SelectionKey selectionKey = reactorThread.register(socketChannel);//将获取到的客户端连接socketChannel 注册到 i/o线程 中的seletor上
                    selectionKey.interestOps(SelectionKey.OP_READ);//同时定义一个感兴趣的事件
                    System.out.println(Thread.currentThread().getName() + "收到新连接" + socketChannel.getRemoteAddress());
                }
            };
        }

    }
    private void bind() throws IOException {
        serverSocketChannel.bind(new InetSocketAddress(8080));
        System.out.println("程序启动完成，端口8080");

    }

    private void initAndRegistr() throws IOException {
        serverSocketChannel= ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        int index = new Random().nextInt(mainReactorThreads.length);
        mainReactorThreads[index].doStart();//随机选取一个，并启动
        SelectionKey selectionKey = mainReactorThreads[index].register(serverSocketChannel);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
    }

    public static void main(String[] args) throws IOException {
        NIOServerV3 nioServerV3 = new NIOServerV3();
        nioServerV3.newGroup();//1.创建main和sub两组线程
        nioServerV3.initAndRegistr();//2.创建serverSocketChannel,注册到mainReactor线程的selector上
        nioServerV3.bind();// 3. 为serverSocketChannel绑定端口
    }
}

package com.lixl.study.IO.nio.netty.demo.server;

import com.lixl.study.IO.nio.netty.demo.handler.XHandlerDecode;
import com.lixl.study.IO.nio.netty.demo.handler.XHandlerPrint;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author : lixl
 * @date : 2020-06-11 16:47:05
 **/
public class XNettyService {
    public static void main(String[] args) throws InterruptedException {

        //TODO 1.线程定义
        // accept 处理连接的线程池
        EventLoopGroup acceptGroup = new NioEventLoopGroup();

        //read io 处理数据的线程池
        EventLoopGroup readGroup = new NioEventLoopGroup();


        //XHandlerPrint专属线程池
        EventLoopGroup xhandlerPrintGroup = new NioEventLoopGroup();

        //TODO 优化1:对象复用（线程安全才可以）
        XHandlerPrint xHandlerPrint = new XHandlerPrint();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(acceptGroup, readGroup);

            //TODO 2.选择TCP协议，NIO实现方式
            serverBootstrap.channel(NioServerSocketChannel.class);

            //TODO childHandler 配置处理请求
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                //职责链定义（请求收到后怎么处理）
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //3.责任链定义（请求收到后怎么处理）
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    //TODO 增加解码器  网络数据-->应用中 这叫解码decode   反之 应用中数据结构--->网络中传输的字节数据 这叫编码encode
                    pipeline.addLast(new XHandlerDecode());
                    //TODO 打印出内容

                    pipeline.addLast(xhandlerPrintGroup,xHandlerPrint); //优化1：handler复用 优化2：线程池使用 netty写法是用eventGroup(已经实现了线程池)
                }
            });
            //4.绑定端口
            System.out.println("启动成功，端口9999");
            serverBootstrap.bind(9999).sync().channel().closeFuture().sync();
        } finally {
            acceptGroup.shutdownGracefully();
            readGroup.shutdownGracefully();
        }

    }
}

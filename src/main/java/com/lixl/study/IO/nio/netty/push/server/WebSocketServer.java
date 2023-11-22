package com.lixl.study.IO.nio.netty.push.server;

import com.lixl.study.IO.nio.netty.push.test.TestCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;

/**
 * @author : lixl
 * @date : 2020-06-16 09:46:05
 **/
public class WebSocketServer {
    public static int PORT = 9000;
    public static void main(String[] args) {
        EventLoopGroup bossGroup   = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();//不指定线程数的话，默认是cup*2

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR,true)
                .childHandler(new WebSocketServerInitializer())
                .childOption(ChannelOption.SO_REUSEADDR,true);
        // TODO 只用一个线程 只用一个线程 只用一个线程  只用一个线程  只用一个线程 来管理下面的这个 所有端口的所有请求，只有一个线程所以不占内存， 只是多开
        // TODO netty服务器绑定多个端口---这里所有端口(每个端口对应一个serversocketChannel)
        // TODO netty服务器绑定9001-9100 （1到100）这100个端口，这样客户端就可以连接这100个端口来发送消息了
//        for (int i = 0; i < 100; i++) {
//            serverBootstrap.bind(++PORT).addListener(new ChannelFutureListener() {
//                @Override
//                public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                    System.out.println("端口绑定完成:"+channelFuture.channel().localAddress());//TODO xxx:xxx:xx:9001 这里就看到了，每个端口有自己的channel
//                }
//            });
//        }
        //netty绑定一个端口
        serverBootstrap.bind(++PORT).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("端口绑定完成:"+channelFuture.channel().localAddress());
            }
        });

        // 端口绑定完成，启动消息随机推送(测试)
        TestCenter.startTest();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}

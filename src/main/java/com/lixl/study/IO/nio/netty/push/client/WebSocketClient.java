package com.lixl.study.IO.nio.netty.push.client;

import com.lixl.study.IO.nio.netty.push.handler.client.WebSocketClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 比如浏览器
 */
public class WebSocketClient {
    public static void main(String[] args) {

        final String host = "127.0.0.1";
        int port = 9001;
        final String maxSize =  "100";
        final String maxConnection = "5";
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_REUSEADDR,true);//TODO 一般情况下不需要,因为测试需要在同一台机器上（本机）模拟60000个链接----------reuse address 表示地址复用
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new HttpClientCodec());
                pipeline.addLast(new HttpObjectAggregator(8192));
                pipeline.addLast(WebSocketClientCompressionHandler.INSTANCE);
                pipeline.addLast("webSocketClientHandler",new WebSocketClientHandler());
            }
        });

        try {
//            客户端绑定多个端口
//            for (int i = 0; i < 100; i++) {//TODO 针对于服务器开启的(9001-9100 1到100号)这100个端口，每个端口发起6w次连接
//                for (int j = 0; j < 60000; j++) {
//                    b.connect(host,port).sync().get();//TODO 客户端会从本机选择一个空闲的端口，不需要指定，客户端会自动选择
//                }
//                port ++;
//            }
            //客户端绑定一个端口
          b.connect(host,port).sync().get();
            System.in.read();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        group.shutdownGracefully();
    }
}

package com.lixl.study.RPC_diy.serverSide;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.IOException;
import java.nio.channels.Channel;

/**
 * @author : lixl
 * @date : 2021-01-14 10:31:34
 **/
public class NettyRpcService extends RpcServer {
    private Channel channel;

    public NettyRpcService(int port, String protocol, RequestHandler handler) {
        super(port, protocol, handler);
    }

    @Override
    void start() {


            //配置服务器
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<ServerChannel>() {
                @Override
                protected void initChannel(ServerChannel serverChannel) throws Exception {
                    ChannelPipeline p = serverChannel.pipeline();
                    p.addLast(new ChannelRequestHander());
                }
            });

            //启动服务
            ChannelFuture f = b.bind(port).sync();

            //等待服务通道关闭
            f.channel().closeFuture().sync();
            channel = (Channel) f.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            try {
                this.channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    void stop() {

    }

    private class ChannelRequestHander extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

            System.out.println("激活");
        }
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("服务端收到消息："+msg);
            ByteBuf msgBuf = (ByteBuf) msg;
            byte[] request = new byte[msgBuf.readableBytes()];//把请求消息转为byte数组
            msgBuf.readBytes(request);

            byte[] response = handler.handlerRequest(request);
            System.out.println("响应："+msg);

            //把响应数据的发送出去
            ByteBuf responseBuf = Unpooled.buffer(response.length);
            responseBuf.writeBytes(response);
            ctx.write(responseBuf);


        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//            ctx.fireChannelReadComplete();
            ctx.flush();
        }
    }
}

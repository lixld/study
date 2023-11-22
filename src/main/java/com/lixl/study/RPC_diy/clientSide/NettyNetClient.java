package com.lixl.study.RPC_diy.clientSide;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;

import java.util.concurrent.CountDownLatch;

/**
 * @author : lixl
 * @date : 2021-01-13 22:02:51
 **/
@Data
public class NettyNetClient implements NetClient {

    /**
     *
     * 只是干了数据传输这么一件事，（数据)发送端----->(数据)接收端
     * 仅仅是数据传输
     * 仅仅是数据传输
     * 仅仅是数据传输
     * 仅仅是数据传输
     * @param data
     * @param serverInfo
     * @return
     */
    @Override
    public byte[] sendRequest(byte[] data, ServiceInfo serverInfo) {
        String[] split = serverInfo.getAddress().split(":");//TODO 数据传输的目的地---ip+port  那边正在（用长连接）等待着数据的到来。。。
        SendHandlder sendHandler = new SendHandlder();
        EventLoopGroup group = new NioEventLoopGroup();
        byte[] rspData = new byte[0];
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(sendHandler);
                        }
                    });
            b.connect(split[0], Integer.valueOf(split[1])).sync();
            rspData = sendHandler.rspData;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return rspData;
    }

    private class SendHandlder extends ChannelInboundHandlerAdapter {
        private CountDownLatch countDownLatch = null;
        private Object readMsg = null;
        private byte[] data;
        private byte[] rspData;

        public CountDownLatch getCountDownLatch() {
            return countDownLatch;
        }

        public void setCountDownLatch(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }
    }
}

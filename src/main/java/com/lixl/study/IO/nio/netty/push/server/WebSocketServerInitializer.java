package com.lixl.study.IO.nio.netty.push.server;

import com.lixl.study.IO.nio.netty.push.handler.server.NewConnectHandler;
import com.lixl.study.IO.nio.netty.push.handler.server.WebSocketServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class WebSocketServerInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65535));
        pipeline.addLast(new WebSocketServerHandler());
        pipeline.addLast(new NewConnectHandler());
    }
}

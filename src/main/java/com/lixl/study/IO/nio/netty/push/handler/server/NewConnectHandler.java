package com.lixl.study.IO.nio.netty.push.handler.server;

import com.lixl.study.IO.nio.netty.push.test.TestCenter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.AttributeKey;

import java.util.List;
import java.util.Map;

public class NewConnectHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest request) throws Exception {

        //解析请求，判断token ,拿到用户ID
        Map<String, List<String>> parameters = new QueryStringDecoder(request.uri()).parameters();
        //String token = parameters.get("token").get(0); 不是所有人都能连接，比如需要登录之后，发送一个推送的token
        String userId = parameters.get("userId").get(0);
        channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).getAndSet(userId);
        TestCenter.saveConnection(userId,channelHandlerContext.channel());//保存连接

    }
}

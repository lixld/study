package com.lixl.study.IO.nio.netty.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : lixl
 * @date : 2020-06-11 17:00:07
 **/
@ChannelHandler.Sharable //优化1：对象共享  （线程安全的可以共享）
public class XHandlerPrint extends ChannelInboundHandlerAdapter {
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.flush();
    }

//    ExecutorService executorService = Executors.newCachedThreadPool();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{

        ByteBuf buf = (ByteBuf) msg;
        byte[] content = new byte[buf.readableBytes()];
        buf.readBytes(content);
        //优化2：线程池使用
        //TODO 耗时的业务操作 ---但是不要这么写，用netty的优雅写法EventLoop
//        executorService.submit(()->{
//
//        });
        System.out.println(Thread.currentThread()+" :最终打印 "+new String(content));
        //后续没有handler处理流程
        buf.release();//TODO 注意点：引用计数减一
//        ctx.fireChannelRead("");//

        // 1兆---堵住了netty通道，堵住了网络缓冲区
        //优化3:分批推送 防止I/O线程被一个连接长时间占用。导致用户体验差
        for (int i = 0; i < 1024; i++) { //打散  每次调用channel 1kb的数据
            ctx.channel().writeAndFlush("");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        cause.printStackTrace();
        ctx.close();
    }
}

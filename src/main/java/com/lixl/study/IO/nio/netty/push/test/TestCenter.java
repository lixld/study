package com.lixl.study.IO.nio.netty.push.test;


import com.lixl.study.IO.nio.netty.push.handler.server.WebSocketServerHandler;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//正常情况是：后台系统通过接口请求，把数据丢到对应的MQ队列，再由推送服务读取
public class TestCenter {
    //此处假设一个用户一台设备，否则用户的通道应该是多个
    //TODO 还应该有一个定时任务，用户检测失效的连接（类似缓存中的LRU算法，长时间不使用就拿出来检测一下是否
    static ConcurrentHashMap<String, Channel> userInfos = new ConcurrentHashMap<>();

    final static byte[] JUST_TEST = new byte[1024];

    //保存信息
    public static void saveConnection(String userId, Channel channel) {
        userInfos.put(userId, channel);
    }

    //    @RabbitListener(queues = "queue-1-ip-port-id")
//    public void consumer(){
//        //1.收到MQ的消息之后
//        //2.解析消息内容，找到目标id
//        //3.根据id找到该用户的连接
//        //4.数据推送（channel.write)
//    }
    //退出时移除
    public static void removeConnection(Object userId) {
        if (userId != null) {
            userInfos.remove(userId.toString());
        }
    }

    public static void startTest() {

        //发送给客户端的消息内容
        System.arraycopy("hello from server Schedule".getBytes(), 0, JUST_TEST, 0, 26);

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {

            //压力测试，在用户中随机抽取1/10进行发送
            if (userInfos.isEmpty()) {
                return;
            }

            ConcurrentHashMap.KeySetView<String, Channel> keySetView = userInfos.keySet();
            String[] keys = keySetView.toArray(new String[]{});
            System.out.println(WebSocketServerHandler.counter.sum() + " :当前用户数量：" + keys.length);
            int size = userInfos.size();
            int sendTousers = size > 10 ? size / 10 : size;//总量的1/10

            for (int i = 0; i < sendTousers; i++) {//循环这么多次
                String key = keys[new Random().nextInt(size)];//从建立连接的用户列表中，随机选取一个用户
                Channel channel = userInfos.get(key);//找到该用户的通道channel
                if (channel == null) {
                    continue;
                }
                if (!channel.isActive()) {//如果通道已不活动（也就是下线之类），则直接从列表中移除
                    userInfos.remove(key);
                    continue;
                }
                channel.eventLoop().execute(() -> {
                    //通过 通道channel 发送消息给客户端
                    channel.writeAndFlush(new TextWebSocketFrame(new String(JUST_TEST)));
                });
            }

        }, 1000L, 2000L, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        startTest();
    }
}

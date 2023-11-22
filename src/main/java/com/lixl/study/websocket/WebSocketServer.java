/*
package com.lixl.study.websocket;


import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

*/
/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 *//*

@Slf4j
@Component
@ServerEndpoint("/websocket/{sessionId}")
public class WebSocketServer {
    @Autowired
    private RedisService redisService;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketServer> webSocketServerSet = new CopyOnWriteArraySet<WebSocketServer>();
//    private static ConcurrentHashMap<Session,WebSocketServer> webSocketServerMap = new ConcurrentHashMap<Session,WebSocketServer>();

    private Session session;

    private String sessionId = "";

    */
/**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     *//*

    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") String sessionId) {
        this.session = session;
        this.sessionId = sessionId;
//        webSocketServerMap.put(session,this);
        webSocketServerSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        logger.info("有新连接加入！当前在线人数为{}", getOnlineCount());
    }

    */
/**
     * 连接关闭调用的方法
     *//*

    @OnClose
    public void onClose(Session session) {
        webSocketServerSet.remove(this);  //从set中删除
//        webSocketServerMap.remove(this.session);
        subOnlineCount();           //在线数减1
//        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        logger.info("有一连接id:{}关闭！当前在线人数为{}", session.getId(), getOnlineCount());
    }

    */
/**
     * 你来我往的方式
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数---发送来消息的用户
     *//*

    @OnMessage
    public void onMessage(String message, Session session) {
//        System.out.println("来自客户端" + session.getId() + "的消息:" + message);
        logger.info("来自客户端{}的消息：{}", session.getId(), message);

        //服务端做出响应 --单发消息给该用户
        message = "服务端收到消息{" + message + "}后，做出的响应--单发消息给该用户";
        sendMessage2SingleUser(message, session);


        message = "服务端收到消息后，做出的响应--群发消息给所有人";
        sendMessage2AllUsers(message);
    }


    */
/**
     * 发生错误时调用
     *
     * @param session
     * @param error
     *//*

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("用户{}发送消息发生错误时调用{}", session.getId(), error.getMessage());
        error.printStackTrace();
    }


    */
/**
     * 群发
     * 自定义消息
     *//*

    public void sendMessage2AllUsers(String message) {
        info(message);
        Iterator<WebSocketServer> iterator = webSocketServerSet.iterator();
        while (iterator.hasNext()) {
            WebSocketServer webSocketServer = iterator.next();
            try {
                Session session = webSocketServer.session;
                sendMessage2SingleUser(message, session);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    */
/**
     * 单发消息
     * 发送消息
     *
     * @param message
     *//*

    public void sendMessage2SingleUser(String message, Session session) {

        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    */
/**
     *
     * 直接在需要的地方，注入这个实体，调用该方法即可（如果是springboot 则直接@Resource）
     * 脱离客户端的触发，直接主动发送消息给客户端
     * 推送消息和当前socket通道的会话id (客户)
     * 实现服务器主动推送
     *//*

    public void sendMessage(String message,@PathParam("sessionId") String sessionId) throws IOException {
        logger.info("推送消息到窗口"+sessionId+"，推送内容:"+message);

        Iterator<WebSocketServer> iterator = webSocketServerSet.iterator();
        while (iterator.hasNext()){
            WebSocketServer webSocketServer = iterator.next();
            if (sessionId == null) {//广播
                webSocketServer.session.getBasicRemote().sendText(message);
            }else {//单播
                String sessionId1 = webSocketServer.sessionId;
                if (sessionId.equals(sessionId1)){//只发给该用户
                    webSocketServer.session.getBasicRemote().sendText(message);
                }
            }
        }

        try {//推送消息和当前socket通道的会话id (客户)
            this.session.getBasicRemote().sendText(message + "==" + sessionId);
        } catch (IOException e) {
            logger.error("websocket异常: " + e.toString());
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    public static synchronized void addOnlineCount() {
        onlineCount.addAndGet(1);
    }

    public static synchronized void subOnlineCount() {
        onlineCount.decrementAndGet();
    }
}
*/

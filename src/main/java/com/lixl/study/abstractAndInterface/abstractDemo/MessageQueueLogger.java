package com.lixl.study.abstractAndInterface.abstractDemo;

public class MessageQueueLogger extends Logger {
    private MessageQueueClient msgQueueClient;

    public MessageQueueLogger(String name, boolean enabled, Level minPermittedLevel, MessageQueueClient msgQueueClient) {
        super(name, enabled, minPermittedLevel);
        this.msgQueueClient = msgQueueClient;
    }

    @Override
    protected void doLog(Level level, String mesage) { // 格式化level和message,输出到消息中间件
        // msgQueueClient.send(...);
    }
}

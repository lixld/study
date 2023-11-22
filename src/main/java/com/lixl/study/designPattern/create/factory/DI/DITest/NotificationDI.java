package com.lixl.study.designPattern.create.factory.DI.DITest;

public class NotificationDI {

    private IMessageSender IMessageSender;

    // 通过构造函数将messageSender传递进来
    public NotificationDI(IMessageSender IMessageSender) {
        this.IMessageSender = IMessageSender;
    }

    public void sendMessage(String cellphone, String message) {
        this.IMessageSender.send(cellphone, message);
    }

    public static void main(String[] args) {
        IMessageSender IMessageSender = new SmsSender();
        NotificationDI notification = new NotificationDI(IMessageSender);
        notification.sendMessage("15022348585","短信验证码：2348");
    }
}


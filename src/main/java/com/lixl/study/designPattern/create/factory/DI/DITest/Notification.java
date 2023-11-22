package com.lixl.study.designPattern.create.factory.DI.DITest;

public class Notification {
    private IMessageSender IMessageSender;

    public Notification() {
//        this.IMessageSender = new IMessageSender();//此处有点像hardcode
        this.IMessageSender = new SmsSender();//此处有点像hardcode
    }

    public void sendMessage(String cellphone, String message) {
        this.IMessageSender.send(cellphone, message);
    }

    public static void main(String[] args) {
        // 使用Notification
        Notification notification = new Notification();
    }
}

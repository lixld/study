package com.lixl.study.designPattern.struct.bridge.notifyDemo;


import com.lixl.study.designPattern.struct.bridge.notifyDemo.notify.Notification;
import com.lixl.study.designPattern.struct.bridge.notifyDemo.notify.NotificationNormal;
import com.lixl.study.designPattern.struct.bridge.notifyDemo.notify.NotificationServer;
import com.lixl.study.designPattern.struct.bridge.notifyDemo.senMsg.MsgSenderEmail;
import com.lixl.study.designPattern.struct.bridge.notifyDemo.senMsg.MsgSenderTelephone;

import java.util.Arrays;
import java.util.List;

/**
 * @author lixinglin
 * @date 10:36
 */
public class TestBridgeDesignPattern {
    public static void main(String[] args) {

        //电话列表
        List<String> telephones = Arrays.asList(new String[]{"15011466678","15504252083"});
//        MsgSenderShortMessage msgSenderShortMessage = new MsgSenderShortMessage(telephones);
        MsgSenderTelephone msgSenderShortMessage = new MsgSenderTelephone(telephones);
        Notification notificationNormal = new NotificationNormal(msgSenderShortMessage);
        //待发送的消息
        String message = "可以忽略的报警";
        notificationNormal.notify(message);


        //邮箱列表
        List<String> emails = Arrays.asList(new String[]{"15011466678@139.com","7646647363@qq.com"});
        MsgSenderEmail msgSender = new MsgSenderEmail(emails);
        notificationNormal =  new NotificationServer(msgSender);
        //待发送的消息
        message = "该消息需要发送邮件报警";
        notificationNormal.notify(message);

    }
}

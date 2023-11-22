package com.lixl.study.IO.bio.demo.push;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * @author : lixl
 * @date : 2020-11-24 09:44:47
 **/
public class PushSocketClient {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost",9999);
            OutputStream outputStream = socket.getOutputStream();
            //消息长度固定位22字节，包含有
            //1.目标ID长度为10
            //2.消息内容字符串长度最多70，按照一个汉字3字节，内容最大长多为210字节
            //10+210=220
            byte[] request = new byte[220];//如果没发满这么多个字节，则就显示是空格
            byte[] userId = "#10000000000".getBytes();
            byte[] content = "测试内容嘻嘻嘻嘻嘻啦所多发所水电费水电费爽肤水%".getBytes();
            System.arraycopy(userId,0,request,0 ,10);
            System.arraycopy(content,0,request,10 ,content.length);
            CountDownLatch  countDownLatch = new CountDownLatch(10);
            for (int i = 0; i < 10; i++) {
                new Thread(()->{
                    try {
                        outputStream.write(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }).start();
            }

            countDownLatch.await();
            Thread.sleep(2000l);
            socket.close();
        } catch (IOException e) {


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

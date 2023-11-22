package com.lixl.study.IO.bio.demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


//阻塞io
public class BIOServer {
    static final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("启动服务端口:9999");
        Socket request = serverSocket.accept();//阻塞，如果没有新连接，这段代码不继续往下执行
        System.out.println("收到新连接：" + request.toString());

        threadPoolExecutor.execute(() -> {//用线程池并发
            try {
                InputStream inputStream = request.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String s = "";
                while ((s = bufferedReader.readLine()) != null) {
                    if (s.length() == 0) break;
                    System.out.println(s);
                }
                System.out.println("收到新数据，来自" + request.toString());

                OutputStream outputStream = request.getOutputStream();
                outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                outputStream.write("Content-Length:11\r\n\r\n".getBytes());
                outputStream.write("Hello World".getBytes());
                outputStream.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }


        });


        /**
         * http->request->tomcat->Threadpool->thread->解析http请求->根据url调用对应servlet-springController-->service
         *
         * 移动互联网时代，移动设备各种信号不好，
         * 在0000毫秒--创建了TCP连接，数据一直没提交过去----500毫秒之后请求传递过来了，这段时间线程迟迟不来。老板约了23：00按摩师，结果你没去，一直在这里等待浪费
         *
         * 线程资源的浪费 tomcat 150-200线程
         *
         */
        /*while (true){//只有一个线程在跑，不能并发
            Socket request = serverSocket.accept();//阻塞，如果没有新连接，这段代码不继续往下执行
            byte[] request = new byte[1024];
            request.getInputStream().read(request);
            System.out.println(new String(request));
        }*/

    }
}

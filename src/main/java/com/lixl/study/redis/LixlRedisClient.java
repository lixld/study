package com.lixl.study.redis;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author : lixl
 * @date : 2021-01-30 16:10:32
 **/
public class LixlRedisClient {
    Socket socket;


    public LixlRedisClient(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {

        LixlRedisClient lixlRedisClient = new LixlRedisClient("127.0.0.1", 6379);
        lixlRedisClient.set("hello","word");
        System.out.println("设置成功--------");

        String hello = lixlRedisClient.get("hello");
        System.out.println("获取结果");
        System.out.println(hello);


    }

    //set key value
    private String set(String key, String value) {
        //构建数据包
        StringBuilder command = new StringBuilder();

        command.append("*3").append("\r\n");//描述整个请求由几个参数

        command.append("$3").append("\r\n");//第1个参数的长度--命令的长度
        command.append("SET").append("\r\n");//第1个参数的值

        command.append("$").append(key.getBytes().length).append("\r\n");//第2个参数的长度
        command.append(key).append("\r\n");//第2个参数的值

        command.append("$").append(value.getBytes().length).append("\r\n");//第3个参数的长度
        command.append(value).append("\r\n");//第3个参数的值
        byte[] response = new byte[0];


        try {
            //发送命令请求到redis服务器
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(command.toString().getBytes());


            //读取redis服务器响应
            response = new byte[1024];
            socket.getInputStream().read(response);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new String(response);

    }
    //get key
    private String get(String key) {
        //构建数据包
        StringBuilder command = new StringBuilder();

        command.append("*2").append("\r\n");//描述整个请求由几个参数

        command.append("$3").append("\r\n");//第1个参数的长度--命令的长度
        command.append("GET").append("\r\n");//第1个参数的值

        command.append("$").append(key.getBytes().length).append("\r\n");//第2个参数的长度
        command.append(key).append("\r\n");//第2个参数的值

        System.out.println(command.toString());
        byte[] response = new byte[0];


        try {
            //发送命令请求到redis服务器
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(command.toString().getBytes());


            //读取redis服务器响应
            response = new byte[1024];
            socket.getInputStream().read(response);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new String(response);

    }
}

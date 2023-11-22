package com.lixl.study.designPattern.struct.proxy.dynamicProxy.jdk;

import com.lixl.study.designPattern.struct.proxy.dynamicProxy.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ClientTest {
    public static void main(String[] args) {

        //被代理类
        UserService userService = new UserServiceImpl();


        //准备代理类数据
        InvocationHandler userServiceInterceptor = new UserServiceInterceptor(userService);//具体执行增强器

        //代理类
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(
                userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(),
                userServiceInterceptor);


        userServiceProxy.addUser(new User("1",13,"sdf"));

    }
}

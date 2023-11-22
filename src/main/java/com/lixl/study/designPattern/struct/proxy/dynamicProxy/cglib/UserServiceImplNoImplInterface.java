package com.lixl.study.designPattern.struct.proxy.dynamicProxy.cglib;

import com.lixl.study.designPattern.struct.proxy.dynamicProxy.User;

/**
 * cglib 动态代理-- 不需要实现接口
 */
public class UserServiceImplNoImplInterface {

    public void addUser(User user) {
        System.out.println("用户落库");
    }
}

package com.lixl.study.designPattern.struct.proxy.dynamicProxy.jdk;

import com.lixl.study.designPattern.struct.proxy.dynamicProxy.User;

/**
 * jdk 动态代理--要代理的对象必须实现接口
 */
public class UserServiceImpl implements UserService {
    @Override
    public void addUser(User user) {
        System.out.println("用户落库");
    }
}

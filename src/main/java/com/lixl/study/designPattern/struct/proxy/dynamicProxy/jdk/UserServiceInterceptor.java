package com.lixl.study.designPattern.struct.proxy.dynamicProxy.jdk;

import com.lixl.study.designPattern.struct.proxy.dynamicProxy.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserServiceInterceptor implements InvocationHandler {
    private Object realObject;

    public UserServiceInterceptor(Object realObject) {
        this.realObject = realObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (args != null && args.length > 0 && args[0] instanceof User) {
            User user = (User) args[0];
            if (user.getName().length() <= 1) {
                throw new RuntimeException("用户名长度必须大于1");
            }
        }

        Object result = method.invoke(realObject, args);
        System.out.println("用户注册成功");
        return result;
    }
}

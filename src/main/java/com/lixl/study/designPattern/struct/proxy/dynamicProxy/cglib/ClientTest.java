/*
package com.lixl.study.designPattern.struct.proxy.dynamicProxy.cglib;

import com.lixl.study.designPattern.struct.proxy.dynamicProxy.User;
import net.sf.cglib.proxy.Enhancer;


public class ClientTest {
    public static void main(String[] args) {


        //使用Enhancer--使用代理对象
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImplNoImplInterface.class);//首先将被代理类TargetObject设置成父类
        enhancer.setCallback(new UserServiceCglibInterceptor());

        UserServiceImplNoImplInterface cglibProxy = (UserServiceImplNoImplInterface) enhancer.create();

        cglibProxy.addUser(new User("1",13,"sdf"));

    }
}
*/

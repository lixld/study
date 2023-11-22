/*
package com.lixl.study.designPattern.struct.proxy.dynamicProxy.cglib;


import com.lixl.study.designPattern.struct.proxy.dynamicProxy.User;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

*/
/**
 * 拦截器
 * 在调用目标方法时，cglib会回调MethodInterceptor接口方法拦截。来实现你自己的代理逻辑。
 * 类似于jdk中的InvocationHandler
 *//*

public class UserServiceCglibInterceptor implements MethodInterceptor {


    */
/**
     * @param obj  :代理对象，它是目标类的子类
     * @param method 方法
     * @param args 参数
     * @param methodProxy 方法代理，通过此对象可以调用代理对象的方法，也可以调用目标对象（也就是父对象）的方法【通过invokeSuper(o)】
     * @return
     * @throws Throwable
     *//*

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        if (args != null && args.length > 0 && args[0] instanceof User) {
            User user = (User) args[0];
            if (user.getName().length() <= 1) {
                throw new RuntimeException("用户名长度必须大于1");
            }
        }
        //因为cglib原理就是生成目标类的子类，来达到代理的目的。||当然也可以和jdk动态代理那样，直接把真实对象传递进来，然后obj.invoke(),只是下面的写法比较简单
        Object result = methodProxy.invokeSuper(obj, args);//所以，这里的super就是父类，调用父类的方法（也就是真实对象的方法）
        System.out.println("用户注册成功");
        return result;
    }
}
*/

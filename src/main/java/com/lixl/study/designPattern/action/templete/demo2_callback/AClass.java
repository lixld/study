package com.lixl.study.designPattern.action.templete.demo2_callback;

//回调不仅可以应用在代码设计上，在更高层次的架构设计上
/*
        比如，通过三方支付系统来实现支付功能，
        用户在发起支付请求之后，一般不会一直阻塞到支付结果返回，
        而是注册回调接口（类似回调函数，一般是一个回调用的 URL）给三方支付系统，
        等三方支付系统执行完成之后，将结果通过回调接口返回给用户。

        回调可以分为同步回调和异步回调（或者延迟回调）

        同步回调：指在函数返回之前执行回调函数；
        异步回调：指的是在函数返回之后执行回调函数
        */

//ICallback、BClass 类是框架代码，AClass 是使用框架的客户端代码
public class AClass {
    public static void main(String[] args) {
        BClass b = new BClass();
        //我们(也就是Aclass 使用框架的客户端代码)可以通过 ICallback 的process() 函数，也就是说，框架因此具有了扩展的能力。

//        A类事前注册一个函数  Icallback() Function 到B类，A类在调用B类的P函数 process() 时，B类反过来调用A类注册给它的函数F  function,这里的F函数就是"回调函数"
//                A调用B， B反过来调用A，这种调用机制就叫做 "回调"
        b.process(new ICallback() {
            //             A类注册给B的函数F
            //回调对象
            @Override
            public void methodToCallback() {
                System.out.println("Call back me。runnable 就是同步回调，这种回调场景，更像是模板模式，异步回调更像是观察者模式");
            }
        });
        System.out.println("111111111");

        Runtime.getRuntime().addShutdownHook(new ShutDownHook());
    }

    private static class ShutDownHook extends Thread {
        public void run() {
            System.out.println("I am called during shutting down.");
        }
    }
}

package com.lixl.study.designPattern.create.factory.DI.MyDI;

public class Demo {
    public static void main(String[] args) {

//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        MyApplicationContext applicationContext1 = new MyClassPathXmlApplication("beans.xml");
        RateLimiter rateLimiter = (RateLimiter) applicationContext1.getBean("rateLimiter");
        rateLimiter.test();
        //...
    }
}

package com.lixl.study.designPattern.create.singleTone;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 这种多例模式
 * 的理解方式有点类似工厂模式。
 * 它跟工厂模式的不同之处是,多例模式创建的对象都是同一个类的对象,而工厂模式创建的是不同子类的对象，
 */
public class Logger {
    private static final ConcurrentHashMap<String, Logger> instances = new ConcurrentHashMap<>();

    private Logger() {
    }

    public static Logger getInstance(String loggerName) {
        instances.putIfAbsent(loggerName, new Logger());
        return instances.get(loggerName);
    }

    public void log() {
        //...
    }

    //使用示例
    public static void main(String[] args) {
        //l1==l2, l1!=l3
        Logger l1 = Logger.getInstance("User.class");
        Logger l2 = Logger.getInstance("User.class");
        Logger l3 = Logger.getInstance("Order.class");
    }

}



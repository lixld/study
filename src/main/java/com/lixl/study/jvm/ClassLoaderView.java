package com.lixl.study.jvm;

import java.net.MalformedURLException;
import java.net.URL;

public class ClassLoaderView {

    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {
        ClassLoader classLoader = ClassLoaderView.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass("java.lang.String");//核心类
        Class<?> aClass1 = classLoader.loadClass("com.sun.nio.zipfs.ZipCoder");//拓展类
        Class<?> aClass2 = ClassLoaderView.class;//应用类

        System.out.println("核心类库加载器："+aClass.getClassLoader());
        System.out.println("拓展类库加载器："+aClass1.getClassLoader());
        System.out.println("应用程序类库加载器："+aClass2.getClassLoader());

        System.out.println("应用程序加载的父类："+aClass2.getClassLoader().getParent());
        System.out.println("应用程序加载的父类的父类："+aClass2.getClassLoader().getParent().getParent());




    }
}

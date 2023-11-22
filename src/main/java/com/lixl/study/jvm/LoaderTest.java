package com.lixl.study.jvm;

import java.net.URL;
import java.net.URLClassLoader;

public class LoaderTest {
    public static void main(String[] args) throws Exception {

        URL classURL = new URL("file:/Users/lixl/Desktop/");//java文件编译后class文件的位置（jvm类放在位置）

        URLClassLoader parentLoader = new URLClassLoader(new URL[]{classURL});//我们自己指定类加载器
        //其实这就是内部实现【工具：如：ecplice、ideal等做的工作，上面的文件路径已经在配置中做了】
        /**
         * 如果把这个一行放入到循环中，也就是每次循环都使用一个新的classLoader，这样的话，就可以加载到最新的编译后的文件
         */
        while (true) {
            URLClassLoader loader = new URLClassLoader(new URL[]{classURL}, parentLoader);//我们自己指定类加载器
            if (loader == null) {
                break;
            }
            //问题：静态代码块
//            Class<?> aClass = Class.forName("com.lixl.study.jvm.HelloService"); ---这种写法的低层实现就是下面的反射写法newInstance()

            Class<?> clazz = loader.loadClass("HelloService");//加载类 ----并不执行静态代码块
            System.out.println("HelloService所使用的类加载器" + clazz.getClassLoader());

            //反射 xxx.class
            Object newInstance = clazz.newInstance();
            Object value = clazz.getMethod("test").invoke(newInstance);
            System.out.println("调用test方法返回在的值：" + value);
            Thread.sleep(3000L);
            System.out.println();

            //help gc 在配置文件中添加VM参数：-verbose:class
            newInstance = null;
            loader = null;
        }
        System.gc();

        Thread.sleep(10000L);
    }
}

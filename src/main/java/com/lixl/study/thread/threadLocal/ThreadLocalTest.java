/*
package com.lixl.study.thread.threadLocal;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadLocalTest {
    static ThreadLocal<String> value = new ThreadLocal();


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        value.set("1111111111");
        System.out.println(Thread.currentThread().getName() + "线程里获取值：" + value.get());
*
         * 新线程


        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程里获取值：" + value.get());
        }).start();

        Callable callable = () -> {//1.可以运行抛出异常
            Random random = new Random();
            int i = random.nextInt();
            return "callable 中的call方法返回值：" + i;//2。可以返回值，实现线程通讯
        };

        FutureTask futureTask = new FutureTask(callable);

*
         * 新线程


        new Thread(futureTask).start();
        System.out.println("----main线程里获取到futureTask线程里返回的值-----" + futureTask.get() + "   现实了线程通讯");
        ;

    }

    //这里private很重要
    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();

    public void handleRequest(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
        doHandle();
    }

    private void doHandle() {
        try {
            UserInfo userInfo = userInfoThreadLocal.get();
            queryUserAssert(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//这里也很重要--remove
            userInfoThreadLocal.remove();
        }
    }

    private void queryUserAssert(UserInfo userInfo) {
        System.out.println("111111");
    }

}
*/

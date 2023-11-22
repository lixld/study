package com.lixl.study.thread;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * callable的call方法就是在runnable的run()方法中执行的
 *
 * @param <T>
 */
public class MyFutureTask<T> implements Runnable {

    Callable<T> callable;
    T result;
    volatile String state = "NEW";

    //先阻塞的先唤醒
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue();//总结：核心思想

    public MyFutureTask(Callable<T> callable) {
        this.callable = callable;
    }

    /**
     * 这个方法需要阻塞，等下面的run()方法唤醒
     *
     * @return
     */
    T get() {
        if ("END".equals(state)) {
            return result;
        }
        while (!"END".equals(state)){
            waiters.add(Thread.currentThread());
            LockSupport.park();//当前线程挂起
        }

        return result;
    }

    @Override
    public void run() {
        try {
            result = callable.call();//没有执行结束，必须保证result是有值
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            state = "END";
        }
        Thread poll = waiters.poll();
        while (poll != null) {
            LockSupport.unpark(poll);
            poll = waiters.poll();
        }

    }



    //多线程--线程的管理--容易膨胀--服务器内存逐渐消失
    //多线程---使用线程池
    /**
     *JDk中--常用的工具
     * 创建日内未
     */
    //demo中使用，生成环境下不能使用，生成使用ThreadPoolExecutor
    // 无界队列的--无限线程数的
    static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        Callable callable = () -> {
            System.out.println("执行任务11111");
            return "111";
        };
        Callable callable1 = () -> {
            System.out.println("执行任务222");
            return "222";
        };
        MyFutureTask myFutureTask = new MyFutureTask(callable);
        MyFutureTask myFutureTask1 = new MyFutureTask(callable1);
        Thread thread = new Thread(myFutureTask);
        Thread thread1 = new Thread(myFutureTask1);
        thread.start();
        thread1.start();
        Object o = myFutureTask.get();
        Object o1 = myFutureTask1.get();
        System.out.println("o:"+o +"  o1:"+o1);



//

    }


}

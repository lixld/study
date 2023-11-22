package com.lixl.study.thread.thread通讯;

import java.util.concurrent.locks.LockSupport;

public class Demo1_LockSupport {
    static volatile int i = 0;
    static int limit = 10;
    static Thread t1, t2, t3;

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            while (i < limit) {//不要用if,处于等待状态的线程可能会收到错误警报和伪唤醒。。。程序会在没有满足条件的情况下退出
                //伪唤醒：是指线程并非因为notify|notifyAll|unpark等api调用而唤醒，是更底层的原因导致的
                System.out.println("t1:" + (++i));
                LockSupport.unpark(t2);//设计者考虑到：如果t2还没有park过，则没事，直接过
                LockSupport.park();
            }
        });
        t2 = new Thread(() -> {//不要用if
            while (i < limit) {
                System.out.println(" t2:" + (++i));
                LockSupport.unpark(t3);
                LockSupport.park();
            }
        });

        t3 = new Thread(() -> {//不要用if
            while (i < limit) {
                System.out.println("  t3:" + (++i));
                LockSupport.unpark(t1);
                LockSupport.park();
            }
        });
        try {
            t1.start();
            /**
             * 一秒钟以后t2开始启动
             * 加这个是为了保证，t1.t2.t3执行的顺序为1-2-3,否则可能会出现乱序
             */
            Thread.sleep(1000l);
            t2.start();

            /**
             * 一秒钟以后t3开始启动
             * 加这个是为了保证，t1.t2.t3执行的顺序为1-2-3,否则可能会出现乱序
             */
            Thread.sleep(1000l);
            t3.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }




}

package com.lixl.study.thread.thread通讯;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo1_CAS {
    static AtomicInteger i = new AtomicInteger(0);
    static volatile int t = 1;
    static int limit = 10;

    public static void main(String[] args) {

        new Thread(() -> {
            while (i.get() < limit) {
                while (t != 1) {//空转，执行事务的不是1号线程

                }
                System.out.println("t1:" + (i.addAndGet(1)));
                t = 2;//1打印完就轮到2去工作了
            }
        }).start();

        new Thread(() -> {
            while (i.get() < limit) {
                while (t != 2) {

                }
                System.out.println(" t2:" + (i.addAndGet(1)));
                t = 3;//2打印完就轮到3去工作了
            }
        }).start();

        new Thread(() -> {
            while (i.get() < limit) {
                while (t != 3) {

                }
                System.out.println("  t3:" + (i.addAndGet(1)));
                t = 1;//3打印完就轮到1去工作了
            }
        }).start();
    }
}

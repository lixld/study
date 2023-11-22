package com.lixl.study.thread.thread通讯;

import java.util.concurrent.locks.LockSupport;

public class Demo1_AllinOne {
    static volatile Integer i = 0;
    static  volatile Integer threadNO = 1;
    static int limit =10;

    static Thread t1,t2,t3;
    public static void main(String[] args) throws InterruptedException {


        //TODO 实现三个线程交替实现打印
       /* Thread thread1 = new Thread(() -> {
            while (i<limit) {
                while (threadNO != 1) {

                }
                System.out.println(" 线陈1:" + (++i));
                threadNO = 2;
            }
        });

        Thread thread2 = new Thread(() -> {
            while (i<limit) {
                while (threadNO != 2) {

                }
                System.out.println(" 线陈2:" + (++i));
                threadNO = 3;
            }
        });

        Thread thread3 = new Thread(() -> {
            while (i<limit) {
                while (threadNO != 3) {

                }
                System.out.println(" 线陈3:" + (++i));
                threadNO = 1;
            }
        });

        thread1.start();
        Thread.sleep(1000l);

        thread2.start();
        Thread.sleep(1000l);
        thread3.start();*/


        //region Locksupport
        t1 = new Thread(() -> {
            while (i<limit) {
                System.out.println(" ---线陈1:" + (++i));
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });
        t2 = new Thread(() -> {
            while (i<limit) {
                System.out.println(" ---线陈2:" + (++i));
                LockSupport.unpark(t3);
                LockSupport.park();
            }
        });
        t3 = new Thread(() -> {
            while (i<limit) {
                System.out.println(" ---线陈3:" + (++i));
                LockSupport.unpark(t1);
                LockSupport.park();
            }
        });

        t1.start();
        Thread.sleep(1000l);
        t2.start();
        Thread.sleep(1000l);
        t3.start();
        //endregion





        //region Description
        /*Object staticObject = new Object();
        t1 = new Thread(() -> {
            while (i<limit) {
                while (i%3==0){
                    synchronized (staticObject){
                        System.out.println(" ---线陈1:" + (++i));
                        try {
                            staticObject.notifyAll();
                            staticObject.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        });
        t2 = new Thread(() -> {
            while (i<limit) {
                while (i%3==1){
                    synchronized (staticObject){
                        System.out.println(" ---线陈2:" + (++i));
                        try {
                            staticObject.notifyAll();
                            staticObject.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        });
        t3 = new Thread(() -> {
            while (i<limit) {
                while (i%3==2){
                    synchronized (staticObject){
                        System.out.println(" ---线陈3:" + (++i));
                        try {
                            staticObject.notifyAll();
                            staticObject.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();*/
        //endregion


    }




}

package com.lixl.study.thread.thread通讯;

public class Demo1_WaitNotify3 {
    static int i = 0;
    static int limit =30;
    static Object object = new Object();
    static Thread t1,t2,t3;
    public static void main(String[] args) {
        t1 = new Thread(() -> {
            while (i<limit) {
                while (i%3==0){
                    synchronized (object){
                        System.out.println(" ---线陈1:" + (++i));
                        try {
                            object.notifyAll();
                            object.wait();
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
                    synchronized (object){
                        System.out.println(" ---线陈2:" + (++i));
                        try {
                            object.notifyAll();
                            object.wait();
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
                    synchronized (object){
                        System.out.println(" ---线陈3:" + (++i));
                        try {
                            object.notifyAll();
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}

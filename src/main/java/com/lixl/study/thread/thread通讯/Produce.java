package com.lixl.study.thread.thread通讯;

public class Produce {


    public static Object object = null;

    public void waitNofiy() throws InterruptedException {
        new Thread(()->{
            if(object == null){
                try {
                    Thread.sleep(100l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (this){
                    System.out.println("进入 等待");
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("消费");
        }).start();

        Thread.sleep(500l);
        object = new Object();
        synchronized (this){
            this.notifyAll();
            System.out.println("通知消费者");

        }
    }

}

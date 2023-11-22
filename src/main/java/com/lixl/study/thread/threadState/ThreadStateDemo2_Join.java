package com.lixl.study.thread.threadState;
public class ThreadStateDemo2_Join {

    public static void main(String[] args) throws InterruptedException {
        Thread girlThread = new Thread(() -> {
            try {
                Thread.sleep(10000l);//运行10秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread boyThread = new Thread(() -> {
            try {
                System.out.println("boy中等待girl运行5秒(5000l)");
                //join相当于插队执行的意思（临时把cpu使用权让给插入的线程）
                girlThread.join(5000l);//boy等待girl  5秒--因为girl需要10秒，所有肯定执行不完
                System.out.println("boy中一直等girl的join()");
                girlThread.join();//t2等待t1 执行完
                System.out.println("boy执行完");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        girlThread.start();
        boyThread.start();
        Thread.sleep(1000l);//1秒
        System.out.println("boy的状态："+boyThread.getState());//TIMED_WAITING

        Thread.sleep(11000l);
        System.out.println("boy的状态------"+boyThread.getState());//TERMINATED



    }
}

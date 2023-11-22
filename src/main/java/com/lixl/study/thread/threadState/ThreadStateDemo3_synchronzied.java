package com.lixl.study.thread.threadState;

public class ThreadStateDemo3_synchronzied {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (ThreadStateDemo3_synchronzied.class){
                System.out.println("t1抢到锁");
            }
        });

        synchronized (ThreadStateDemo3_synchronzied.class){
            t1.start();
            System.out.println("t1 抢不到锁的状态："+t1.getState());//--RUNNABLE
            Thread.sleep(5000l);
            System.out.println("t1 抢不到锁的状态："+t1.getState());//--BLOCKED

        }
        Thread.sleep(2000l);
        System.out.println(t1.getState());

    }

}

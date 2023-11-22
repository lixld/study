package com.lixl.study.thread.countDownLatch;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class MyCountDownLatch {
    AtomicInteger count = null;

    //需要锁池
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue();

    public MyCountDownLatch(int count) {
        AtomicInteger count1 = new AtomicInteger(count);
        this.count = count1;
    }

    public void await() {//等待计数器清零
        waiters.add(Thread.currentThread());
        while (count.get() != 0) {
            //挂起线程
            LockSupport.park();//挂起，等待被唤醒
        }
        waiters.remove(Thread.currentThread());
    }

    public void countDown() {//计数器-1
        int andDecrement = count.getAndDecrement();
        //释放锁之后，唤醒线程
        if (andDecrement == 0) {
            for (Thread waiter : waiters) {
                LockSupport.unpark(waiter);
            }
        }

    }

    private static void testCountDownLatch() throws InterruptedException {

        //200线程同时来执行
        MyCountDownLatch countDownLatch = new MyCountDownLatch(200);//一共200个计数器
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.countDown();//线程夯在这里,计数器-1，如果计数器没有归0则，线程一直夯在这里不动，阻塞等待
                    System.out.println("线程 "+Thread.currentThread().getName() + " 就绪");
                    countDownLatch.await();//等待计数器归0
                    System.out.println(Thread.currentThread()+"运行了");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        testCountDownLatch();
    }


}

package com.lixl.study.distributeLock;

import java.util.Collection;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DistributeTest {

    /**
     * 分布式锁测试demo
     * @param ts
     */
    private void distributeLockDemo(Collection ts) {
        int number =10;

        Lock lock = new ReentrantLock();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(number);
        for (int i = 0; i < number; i++) {
            new Thread(()->{
                try {
                    System.out.println("-------我准备好了---------");
                    cyclicBarrier.await();
                    lock.lock();
                    System.out.println("--司法所开发几十块雷锋精神老地方");
//                    productImageService.saveBatch(ts);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }).start();
        }
    }





}

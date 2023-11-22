package com.lixl.study.thread.countDownLatch;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

//模板方法模式--抽象实现 AQS---抽象队列同步器
public abstract class Abstract {

    AtomicInteger state = null;//资源数量--共享资源状态，可以被一定数量线程
    //锁的拥有者
    AtomicReference<Thread> owner  = new AtomicReference<>();//独享锁

    //需要锁池
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue();

    public void acquire(){
        //进入等待队列
        waiters.add(Thread.currentThread());

        //TODO 获取资源
        while(tryAcquire()){
            // 没有获取到资源后续流程是一样的==挂起线程
            LockSupport.park();//挂起线程
        }

        waiters.remove(Thread.currentThread());
    }

    public void acquireShare(){
        //进入等待队列
        waiters.add(Thread.currentThread());

        //TODO 获取资源


        waiters.remove(Thread.currentThread());
    }


    public void release(){
        if (tryRelease()){
            for (Thread waiter : waiters) {
                LockSupport.unpark(waiter);
            }
        }
    }
    public void releaseShare(){
        if (tryReleaseShare()){
            for (Thread waiter : waiters) {
                LockSupport.unpark(waiter);
            }
        }
    }

    /**
     * 这个方法的实现，交给具体的使用者
     * @return
     */
    public abstract  boolean tryAcquire();

    /**
     * 释放资源
     * @return
     */
    public abstract  boolean tryRelease();


    protected abstract boolean tryReleaseShare();

}

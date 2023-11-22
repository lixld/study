package com.lixl.study.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 优化---C++ ,运行时，只能推论
 * 手动实现synchronized
 * synchronized关键字的原理 owner
 * 对象监视器
 */
public class DemoLock {

    //锁的拥有者-----CAS实现
    AtomicReference<Thread> owner = new AtomicReference<>();

    //需要锁池
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue();

    public void lock() {
        //这样就是公平的了，来了先排队
//        if (waiters.size()>0){
//            waiters.add(Thread.currentThread());
//            LockSupport.park(Thread.currentThread());
//        }
        //CAS --此处直接CAS 是一种非公平实现
        while (!owner.compareAndSet(null, Thread.currentThread())) {//拿到锁，去执行代码
            //没拿到锁，等待
            waiters.add(Thread.currentThread());
            LockSupport.park();//挂起，非常形象（当前运行的线程，进入停车场）等待被唤醒
        }
        waiters.remove(Thread.currentThread());
    }

    public void unlock() {
        if (owner.compareAndSet(Thread.currentThread(), null)) {
            //释放锁之后，要唤醒一个线程
            Thread next = waiters.peek();

            LockSupport.unpark(next);
        }
    }


}

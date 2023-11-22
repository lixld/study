package com.lixl.study.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class myLock implements Lock {
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
//        lock.lock();
//        boolean b = lock.tryLock(10, TimeUnit.MILLISECONDS);
//        if (b) {
//            System.out.println("抢到锁");
//        }

    }

    /**
     * 锁的状态--多线程并发调用  0-未占用 大于0-占用 --重入次数
     */
    AtomicInteger atomicInteger = new AtomicInteger();
    Thread ownerThread = new Thread();
    LinkedBlockingQueue waiters = new LinkedBlockingQueue();

    @Override //没有拿到锁，就排队等待
    public void lock() {
        if (!tryLock()) {
            //1.排队
            //2.等待
            waiters.add(Thread.currentThread());
            while (true) {//不停测尝试获取锁
                //如果某一个线程抢到了锁，自己把自己动队列里挪出来
                /**
                 * 如果想要公平，则需要加个判断，队列里是不是有在排队的
                 */
                if (tryLock()) {//马上就去获取锁，没有排队，所有之类是非公平锁
                    waiters.poll();//取---走，队列中就不存在了
                    return;
                }
                LockSupport.park();//等着。等到何时在执行？唤醒的目的是继续抢
            }

        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        //把0-1
        if (atomicInteger.get() == 0) {
            //CAS底层 c++实现，保证N个线程同时操作，只有一个成功
            boolean b = atomicInteger.compareAndSet(0, 1);
            ownerThread = Thread.currentThread();
            if (b) return true;
        } else if (ownerThread == Thread.currentThread()) {//如果占用线程是当前线程，则代表可重入
            atomicInteger.incrementAndGet();
            return true;
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        if (ownerThread != Thread.currentThread()) {
            try {
                throw new Exception("invalid unlock()");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (atomicInteger.decrementAndGet() == 0) {
            ownerThread = null;
        }
        Thread oneWaiter = (Thread) waiters.peek();//获取队列中的第一个（但是队列中仍然存在）
        if (oneWaiter != null) {
            LockSupport.unpark(oneWaiter);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}

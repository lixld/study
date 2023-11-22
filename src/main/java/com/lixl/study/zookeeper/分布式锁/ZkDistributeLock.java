package com.lixl.study.zookeeper.分布式锁;

import com.lixl.study.zookeeper.MyZkSerializer;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 惊群效应
 */
public class ZkDistributeLock implements Lock {

    private String lockPath;
    private ZkClient zkClient;
    //锁重入计数
    private ThreadLocal<Integer> reentrantCount = new ThreadLocal<>();

    public ZkDistributeLock(String lockPath) {
        this.lockPath = lockPath;
        zkClient = new ZkClient("localhost:2181");
        zkClient.setZkSerializer(new MyZkSerializer());
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            //没获得锁，阻塞自己
            waitForLock();
            //再次尝试
            lock();
        }
    }

    private void waitForLock() {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("收到节点被删除（也就是释放了锁），那么需要唤醒自己，继续去挣抢锁");
                countDownLatch.countDown();
            }
        };
        zkClient.subscribeDataChanges(lockPath, listener);


        //阻塞自己
        if (this.zkClient.exists(lockPath)) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //取消注册
        zkClient.unsubscribeDataChanges(lockPath, listener);

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() { //不会阻塞

        Integer integer = this.reentrantCount.get();
        if (integer != null) {
            if (integer > 0) {
                this.reentrantCount.set(++integer);
                return true;
            }
        }
        //创建节点
        try {
            zkClient.createEphemeral(lockPath);
            this.reentrantCount.set(1);
        } catch (ZkNodeExistsException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        //重入的释放锁处理
        Integer integer = this.reentrantCount.get();
        if (integer != null) {
            if (integer > 1) {
                this.reentrantCount.set(--integer);
                return;
            } else {
                this.reentrantCount.set(null);
            }
        }


        zkClient.delete(lockPath);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public static void main(String[] args) {
        //并发数 50
        int currency = 50;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(currency);
        for (int i = 0; i < currency; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "-----我准备好了----------");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    ZkDistributeLock zkDistributeLock = new ZkDistributeLock("/distLock11");
                    try {
                        zkDistributeLock.lock();
                        System.out.println(Thread.currentThread().getName() + " 获得锁!");
                    } finally {
                        zkDistributeLock.unlock();
                    }


                }
            }).start();

        }

    }
}

package com.lixl.study.zookeeper.分布式锁;

import com.lixl.study.zookeeper.MyZkSerializer;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZkDistributeLock2 implements Lock {

    /**
     * 利用临时顺序节点来实现分布式锁
     * 获取锁：取排队号（创建自己的临时顺序节点），然后判断自己是否是最小号？如果是，则获取锁，如果不是则注册前一个节点的watcher,阻塞自己等待
     * 释放锁：删除自己创建的临时顺序节点
     */
    private String lockPath;
    private ZkClient zkClient;

    private ThreadLocal<String> currentPath = new ThreadLocal<>();
    private ThreadLocal<String> beforePath = new ThreadLocal<>();


    //锁重入计数
    private ThreadLocal<Integer> reentrantCount = new ThreadLocal<>();


    public ZkDistributeLock2(String lockPath) {
        this.lockPath = lockPath;
        zkClient = new ZkClient("localhost:2181");
        zkClient.setZkSerializer(new MyZkSerializer());

        if (!zkClient.exists(lockPath)) {
            try {
                //创建好锁节点
                zkClient.createEphemeral(lockPath);
            } catch (RuntimeException e) {
//                e.printStackTrace();
            }
        }
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
//        zkClient.subscribeDataChanges(lockPath, listener);
        /**
         * 注意：这里注册的是前一个节点的watch
         */
        String myBeforePath = this.beforePath.get();
        zkClient.subscribeDataChanges(myBeforePath, listener);


        //阻塞自己
        if (this.zkClient.exists(myBeforePath)) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //取消注册
        zkClient.unsubscribeDataChanges(myBeforePath, listener);

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


        if (currentPath.get() == null) {
            currentPath.set(this.zkClient.createEphemeralSequential(lockPath + "/", "aaa"));
        }

        List<String> children = zkClient.getChildren(lockPath);

        //排序list
        Collections.sort(children);

        String first = children.get(0);//第一名
        if (currentPath.get().equals(lockPath + "/" + first)) {
            this.reentrantCount.set(1);
            return true;
        } else {
            //取到前一个
            //得到字节的索引号
            /**
             * 当前我这个号，在整个丢列中的位置索引（比如：我这个号在整个队伍的第7位，那么我的前一个就是6号）
             */
            int curIndex = children.indexOf(currentPath.get().substring(lockPath.length() + 1));
            beforePath.set(lockPath + "/" + children.get(curIndex - 1));
        }
        return false;


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

                    //模拟多个客户端来连接zookeeper,来抢锁
                    ZkDistributeLock2 zkDistributeLock = new ZkDistributeLock2("/distLock11");
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

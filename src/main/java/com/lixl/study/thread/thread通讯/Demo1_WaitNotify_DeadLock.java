package com.lixl.study.thread.thread通讯;

import java.util.concurrent.locks.LockSupport;

public class Demo1_WaitNotify_DeadLock {

    public static Object baozidian = null;

    public static void main(String[] args) {
        /**
         * 注意：wait/notifyDemo|notifyAll也可能会造成死锁🔐
         * 就是：当生产者已经发起过通知，可是消费者当时并没有在wait状态，导致错过了，就像爱情一样，错过了就失去了
         * 示例代码如下
         */
//        Demo1_WaitNotify_DeadLock demo1_waitNotify = new Demo1_WaitNotify_DeadLock();
//        demo1_waitNotify.waitNotifyTest();
        parkUnPrak();

    }

    /**
     * park | unpark 不释放锁，所以在同步代码块中使用要注意，也会死锁
     */
    private static void parkUnPrak() {
        //消费者
        Thread customer = new Thread(() -> {
            System.out.println("准备去买包子");
            while (baozidian == null) {
                System.out.println("没有包子进入等待");
                LockSupport.park();
            }
            System.out.println("买到包子");
        });
        Thread createrThread = new Thread(() -> {
            while (baozidian == null) {
                baozidian = new Object();
                System.out.println("通知有货了！");
                LockSupport.unpark(customer);
            }
        });

        customer.start();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        createrThread.start();
    }

    /**
     * wait/notiyf因为有顺序，必须是先wait，然后再notify，才能把之前wait的唤醒
     * 所以呢也会出现死锁。下面就是一个死锁示例，
     */
    public void waitNotifyTest() {
        new Thread(() -> {

            //region 死锁写法，（除去这一段就成为正常的示例了）就像赶火车，路上堵车耽误了，当你赶到车站的时候，发现车已经开走了，再也被调用不上了
            try {
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //endregion
            synchronized (this) {
                while (baozidian == null) {
                    try {
                        System.out.println("1.进入等待");
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("2.买到了包子");
        }).start();

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            synchronized (this) {
                while (baozidian == null) {//不要使用if来判定
                    baozidian = new Object();
                    System.out.println("3.生成好了，来买吧！");
                    this.notify();
                }
            }
        }).start();


    }


}

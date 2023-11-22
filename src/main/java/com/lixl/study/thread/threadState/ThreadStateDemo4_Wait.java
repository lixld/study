package com.lixl.study.thread.threadState;

public class ThreadStateDemo4_Wait {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("wait是Object对象的方法" +
                "表示，等待他人来唤醒" +
                "----接着执行【这里非常重要，是继续执行下面的动作（每个线程的线程帧中有程序计数器，用来记录该现象执行到了什么位置），就好像断点一样】-----下面，一堆屌丝等这女神抛出橄榄枝，" +
                "wait释放锁，出让系统资源，进入等待池中等待" +
                "wait，notifyDemo,notifyAll只能用在同步代码块中");

        Object object = new Object();
        Thread t1 = new Thread(() -> {

            /*死锁代码
            try {
                Thread.sleep(60000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            synchronized (object) {
                try {


                    System.out.println("111111t1将要wait(3000l)，------释放了object对象的锁，进入等待锁池中");
                    object.wait(3000l);//闹钟,时间到了以后只是唤醒，并且去争夺锁 != 线程等到锁

                    System.out.println("t1将要wait()，释放了锁");
                    object.wait();

                    System.out.println("t1执行完");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        /**
         * 为了保证t1启动以后，马上执行到，而不是被下面的synchroized方法，把锁抢了去，所有
         * 这里故意做了sleep等待
         */
        Thread.sleep(1000l);

        synchronized (object) {//获得了object对象的锁，开始执行

            System.out.println("222222wait(3000)释放了锁，马上被抢到锁，t1的状态222222：" + t1.getState());//因为thread正在第17行代码中wait(3000l),所以处于timed_waited
            object.notify();//还没到3秒倒计时，提前把他们唤醒，等待锁池中的对象发起一起竞争，去抢锁
            Thread.sleep(1000);//给他们抢的时间
            System.out.println("333333t1的状态333333：" + t1.getState());//被唤醒以后，因为3秒还未到，锁正在当前这个代码块中，所有t1线程正在锁等待池中准备抢锁，所以处于blocked状态

        }

        //此时线程已经被唤醒，所以而且时间3秒也到了，所以会继续执行线程里面的逻辑，再次进入16行，线程有wait()释放了锁，所以此时线程wait
        Thread.sleep(3000l);
        System.out.println("444444t1 的状态11111：" + t1.getState());


        Thread.sleep(1000l);

        synchronized (object) {
            object.notify();//线程再次被唤醒，可以有一个来唤醒
        }

        System.out.println("t1的状态4444444：" + t1.getState());


        Thread.sleep(1000);
        System.out.println("t1的状态5555555：" + t1.getState());

        /*wait是Object对象的方法表示，等待他人来唤醒接着执行下面，一堆屌丝等这女神抛出橄榄枝，wait释放锁，出让系统资源，进入等待池中等待wait，notifyDemo,notifyAll只能用在同步代码块中
        111111t1将要wait(3000l)，------释放了object对象的锁，进入等待锁池中
        222222wait(3000)释放了锁，马上被抢到锁，t1的状态222222：TIMED_WAITING
        333333t1的状态333333：BLOCKED
        t1将要wait()
        444444t1 的状态11111：WAITING
        t1的状态4444444：BLOCKED
                t1执行完
        t1的状态5555555：TERMINATED*/

        /*wait是Object对象的方法表示，等待他人来唤醒接着执行下面，一堆屌丝等这女神抛出橄榄枝，wait释放锁，出让系统资源，进入等待池中等待wait，notifyDemo,notifyAll只能用在同步代码块中
        111111t1将要wait(3000l)，------释放了object对象的锁，进入等待锁池中
        222222wait(3000)释放了锁，马上被抢到锁，t1的状态222222：TIMED_WAITING
        333333t1的状态333333：BLOCKED
        t1将要wait()
        444444t1 的状态11111：WAITING
                t1执行完
        t1的状态4444444：RUNNABLE
        t1的状态5555555：TERMINATED*/

    }
}

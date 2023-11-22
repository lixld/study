package com.lixl.study.thread.countDownLatch;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

//内部类，自己实现Semaphore
    class MySemaphore {
        AtomicInteger count = null;
        //需要锁池
        LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue();


        public MySemaphore(int count) {
            AtomicInteger atomicInteger = new AtomicInteger(count);
            this.count = atomicInteger;
        }

        public void acquire() {
            Thread thread = Thread.currentThread();
            //先加入
            waiters.add(thread);
            for (; ; ) {
                int current = count.get();
                int n = current - 1;//发出一个令牌
                if (current <= 0 || n < 0) {//如果小于0就挂起线程
                    LockSupport.park();
                }

                if (count.compareAndSet(current, n)) {
                    break;
                }
            }
            //获取到则移除
            waiters.remove(thread);
        }

        public void release() {//释放令牌  --- 令牌数+1
            if (count.incrementAndGet()> 0) {

//                Thread waiter = waiters.peek();//这是随机取了一个，也可以全部唤醒（代码如下）
//                LockSupport.unpark(waiter);//唤醒线程继续 抢锁

                ///释放锁之后，唤醒其它等待线程
                for (Thread waiter : waiters) {
                    LockSupport.unpark(waiter);
                }

            }
        }

    }

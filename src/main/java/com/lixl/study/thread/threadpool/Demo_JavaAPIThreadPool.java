package com.lixl.study.thread.threadpool;

import java.util.List;
import java.util.concurrent.*;

public class Demo_JavaAPIThreadPool {

    public void testCommon(ThreadPoolExecutor threadPoolExecutor) throws InterruptedException {
        for (int i = 0; i < 15; i++) {
            int n = i;
            threadPoolExecutor.submit(() -> {
                System.out.println("开始执行：" + n);
                try {
                    Thread.sleep(3000l);
                } catch (InterruptedException e) {
                    System.out.println("异常：" + e.getMessage());
                    e.printStackTrace();
                }
                System.out.println("执行结束：" + n);
            });
            System.out.println("任务提交成功：" + i);
        }
        Thread.sleep(500l);
        System.out.println("刚刚开始线程池线程数量：" + threadPoolExecutor.getPoolSize());
        System.out.println("刚刚开始等待数量：" + threadPoolExecutor.getQueue().size());

        Thread.sleep(15000l);
        System.out.println("15秒后线程池线程数量：" + threadPoolExecutor.getPoolSize());
        System.out.println("15秒后等待数量：" + threadPoolExecutor.getQueue().size());


    }

    /**
     * 核心：5
     * 最大：10
     * 无界对列
     * 非核心线程最大存活时间：5秒
     *
     * @throws InterruptedException
     */
    private void test1() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        testCommon(threadPoolExecutor);
        //
    }

    /**
     * 其实是就是FixThreadPool,固定大小的线程迟
     * 核心：5
     * 最大：5
     * 无界对列
     * 非核心线程最大存活时间：0秒
     *
     * @throws InterruptedException
     */
    private void test3() throws InterruptedException {
//        Executors.newFixedThreadPool(5);//和下面的一样的
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>());
        testCommon(threadPoolExecutor);
        //
    }

    /**
     * 核心：5
     * 最大：10
     * 对列大小为3
     * 非核心线程最大存活时间：5秒
     * 指定拒绝策略
     *
     * @throws InterruptedException
     */
    private void test2() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.err.println("有任务被拒绝");
                    }
                });
        testCommon(threadPoolExecutor);
        //预计结果：
        /*
        1.5个任务直接分配线程开始执行
        2.3个进入等待对列
        3.对列不够用，临时加开5个线程来执行任务（5秒钟没活干就销毁）
        4.对列和线程池都满了，剩下两个任务，没资源了，被拒绝执行
        5.任务执行：5秒后，如果没任务可执行，销毁临时创建的5个线程
         */
    }


    private void test4() throws InterruptedException {
        /**
         * 缓存线程池
         * 任务大小不好控制的情况，动态扩容
         * SynchronousQueue ：同步队列，实际上它不是一个真正的队列，因为它不会为队列中元素维护存储空间，
         * 它维护一组线程，这些线程在等待着把元素加入或者移出队列
         * 线程池中又没有空闲的线程能够从SychronousQueue队列实例中获取一个任务
         * 那么相应的offer方法调用就会失败（即任务没有被放入工作队列）
         * 此时ThreadPoolExecutor就会加开一个线程，用于对这个入队列失败的任务进行处理（假设此时线程池的大小还未达到最大值）
         //        Executors.newCachedThreadPool();//一样的效果
         /**
         * 推荐使用这个，不过线程最大数量不要用Integer。MAX,只需要使用固定大小即可，比如1000或者1w
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60l, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        testCommon(threadPoolExecutor);
        /*
        预计结果
        15个任务提交，马上加开15个线程，在超时时间（60m）内，如果又有新的任务提交进来，15个线程中如果有执行完的，则就马上复用这些已经执行任务的线程，超过超时时间，销毁
        1.线程池数量是15，超出数量的任务，其他的进入队列中等待被执行
        2.所有任务执行结束，如果无任务可执行，所有线程全部被销毁，池的大小恢复为0
         */

        Thread.sleep(60000l);
        System.out.println("60秒后，再看线程池中的数量：" + threadPoolExecutor.getPoolSize());
    }


    private void test5() throws InterruptedException {
        /**
         * 本质是延时队列 DelayWorkQueue延时队列，超出核心线程数量的线程存活时间为：0秒
         * 这里注意一下这个数量，应该大一点，如果同一时间，有多个线程来执行的话，就会放不下
         */
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务被执行，当前时间：" + System.currentTimeMillis());
            }
        }, 3000, TimeUnit.MILLISECONDS);
        int min = Math.min(1, 2);
        // FIXME: 2020-04-21
        /*// STOPSHIP: 2020-04-21
        预计结果:定时任务在3秒后被执行一次
         */
        System.out.println("定时任务，提交成功，时间是：" + System.currentTimeMillis() + "，线程池中的数量：" + scheduledThreadPoolExecutor.getPoolSize());
    }

    private void test6() throws InterruptedException {
        /**
         * 2秒后开始执行
         * 每间隔1秒固定执行一次（如果发现上次执行还未完毕，则等待完毕，完毕后立即执行）
         * 也就是：3秒钟执行一次（计算方式：每次执行3秒，间隔时间1秒，执行结束后马上开始下次执行，无需等待）
         * 核心：选择任务执行|period  二者中的最大值
         */
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(3000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务1被执行，当前时间：" + System.currentTimeMillis());
        }, 2000, 1000, TimeUnit.MILLISECONDS);
        /*
        预计结果:定时任务在3秒后被执行一次
         */
        System.out.println("定时任务，提交成功，时间是：" + System.currentTimeMillis() + "，线程池中的数量：" + scheduledThreadPoolExecutor.getPoolSize());

        /**
         *  效果2：提交后，2秒开始第一次执行，之后每隔1秒，固定执行一次，如果发现上次没有执行完，则等待执行完后。。。。继续推迟一个固定时间来执行
         *  也就是说这个代码效果是每4秒（任务的3秒加dely的1秒）执行一次
         *  核心：
         *  两种情况：
         *  1.任务时间小于间隔：直接选间隔时间
         *  2.任务时间大于间隔：间隔时间+任务时间
         */
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            try {
                Thread.sleep(3000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务2被执行，当前时间：" + System.currentTimeMillis());
        }, 2000, 1000, TimeUnit.MILLISECONDS);
        /*
        预计结果:定时任务在3秒后被执行一次
         */
        System.out.println("定时任务，提交成功，时间是：" + System.currentTimeMillis() + "，线程池中的数量：" + scheduledThreadPoolExecutor.getPoolSize());
    }


    private void test7() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                (r, executor) -> System.err.println("有任务被拒绝"));
        testCommon(threadPoolExecutor);
        Thread.sleep(1000l);
//        threadPoolExecutor.shutdown();
        //预计结果：
        /*
        1.终止之后，再提交任务的不能提交，已经提交的继续运行。
         */

        List<Runnable> runnables = threadPoolExecutor.shutdownNow();
        System.out.println("未结束的任务有：" + runnables.size());
        //预计结果：
        /*
        1.终止之后，再提交任务的不能提交，已经提交的也马上终止执行
         */
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("追加一个任务");
            }
        });

    }

    public static void main(String[] args) throws InterruptedException {
        Demo_JavaAPIThreadPool demo9 = new Demo_JavaAPIThreadPool();
//        demo9.test1();
//        demo9.test3();
//        demo9.test4();
//        demo9.test5();
//        demo9.test6();
        demo9.test7();
        Executors.newScheduledThreadPool(5);
    }
}

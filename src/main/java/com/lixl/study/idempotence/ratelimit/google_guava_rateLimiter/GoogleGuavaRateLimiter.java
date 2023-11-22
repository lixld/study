package com.lixl.study.idempotence.ratelimit.google_guava_rateLimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author : lixl
 * @date : 2020-12-18 09:29:50
 **/
public class GoogleGuavaRateLimiter {
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    /**
     * 单机限速demo
     */
    private static void test3() {
        Test6.updateResourceQps("methodB",5.0);
        try {
            testB();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testB() throws InterruptedException {
        //测试other
        int y = 0;
        while (true) {
            y++;
            long t3 = System.currentTimeMillis();
            System.out.println(" begin:" + t3 + " , tom:" + y);

            int  result2 = Test6.enter("methodB","tom");
            if (result2 == 99) {
                y = 0;
                Thread.sleep(1000);
            }
        }
    }

    private static void testA() throws InterruptedException {
        int i = 0;
        while (true) {
            i++;
            long t2 = System.currentTimeMillis();
            System.out.println(" begin:" + t2 + " , hanchao:" + i);

            int  result = Test6.enter("methodA","hanchao");
            if (result == 99) {
                i = 0;
                Thread.sleep(1000);
            }
        }
    }

    private static void test2() {
        //线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        //速率是每秒只有5个许可
        final RateLimiter rateLimiter = RateLimiter.create(3.0);

        for (int i = 0; i < 10; i++) {
            final int no = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //TODO 阻塞获取许可
                        rateLimiter.acquire();
                        System.out.println("Accessing: " + no + ",time:"+ new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date()));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            //执行线程
            exec.execute(runnable);
        }
        //退出线程池
        exec.shutdown();
    }

    private static void test1() {
        //新建一个每秒限制3个的令牌桶
        RateLimiter rateLimiter = RateLimiter.create(3.0);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //获取令牌桶中一个令牌，最多等待10秒
                    if (rateLimiter.tryAcquire(1, 10, TimeUnit.SECONDS)) {
                        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    }
                }
            });
        }
        executor.shutdown();
    }



}

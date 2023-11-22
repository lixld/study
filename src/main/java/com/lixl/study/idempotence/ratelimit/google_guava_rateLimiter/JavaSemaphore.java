package com.lixl.study.idempotence.ratelimit.google_guava_rateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author : lixl
 * @date : 2020-12-18 10:33:36
 **/
public class JavaSemaphore {
    public static void main(String[] args) {
        //线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        //同一时间-只能3个线程
        final Semaphore semp = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            final int no = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取许可
                        semp.acquire();
                        System.out.println("Accessing: " + no+ " --- " + new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date()));
                        //睡5s
                        Thread.sleep(5000);//睡眠5秒钟

                        //TODO 访问完后,释放许可，如果注释掉下面的语句,则控制台只能打印3条记录,之后线程一直阻塞
                        semp.release();

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

}

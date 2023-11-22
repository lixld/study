package com.lixl.study.thread.countDownLatch;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class semaphoreTest {

    public static void main(String[] args) {

        testSemaphore();
    }



    /**
     * 信号量 ：通过发令牌来限流，一次只能有几个并发数
     * 方法：acquire|tryAcquire + release()
     * @param
     * @throws InterruptedException
     */

    private static void testSemaphore() {

        //TODO 控制并发线程数量
        int rateLimitThreadNumber = 5;//TODO --最多运行同时又几个线程并发执行

        Semaphore semaphore= new Semaphore(rateLimitThreadNumber);
        for (int i = 0; i < 9; i++) {//TODO ---一共有9个客人
            String vipNO = "vip-00"+i;
            new Thread(()->{
                try {
//                    semaphore.acquire();//TODO 获取令牌，没拿到的就阻塞等待  该方法返回void
                    semaphore.tryAcquire(3000, TimeUnit.MILLISECONDS);//TODO 获取令牌，没拿到的就阻塞等待---带有超时时间 该方法返回void
                    service(vipNO);


                    semaphore.release();//服务结束，释放令牌，等待其它用户抢该令牌

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }

    public static void service(String vipNO) throws InterruptedException {
        System.out.println("迎接贵宾一位，编号："+vipNO+" 正在享受服务");
        Thread.sleep(new Random().nextInt(3000));
        System.out.println("欢送贵宾出门，编号："+vipNO);
        Thread.sleep(5000);//模拟服务耗时3秒
    }
}

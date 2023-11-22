package com.lixl.study.thread.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLanuchTest {
    public static void main(String[] args) throws InterruptedException {
        testCountDownLatch();
    }

    /**
     * 如果计数器没有归0则，线程一直夯在这里不动，阻塞等待
     *
     * @throws InterruptedException
     */

    private static void testCountDownLatch() throws InterruptedException {
        //200线程同时来执行
        CountDownLatch countDownLatch = new CountDownLatch(200);//一共200个计数器
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.countDown();//线程夯在这里,计数器-1，如果计数器没有归0则，线程一直夯在这里不动，阻塞等待
                    System.out.println(Thread.currentThread().getName() + " 就绪");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        countDownLatch.await();//等待计数器归0
        System.out.println("全部就绪");
    }
}

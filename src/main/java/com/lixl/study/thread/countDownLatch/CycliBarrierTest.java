package com.lixl.study.thread.countDownLatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CycliBarrierTest {
    private static void testCyclicBarrier() {
        /**
         *
         * 可多次触发
         * 如果计数器每次达到这个就可以执行一次，否则 线程一直夯在这里不动，阻塞等待
         *
         *
         * 应该场景
         * 1.数据量比较大时，实现批量插入数据到数据库
         *
         * 2.数据统计，30个线程统计30天数据，全部统计完毕后，执行汇总
         */
        //300个线程，压力测试。 分成3组，每100个线程为一组
        //调成和线程数字一样就和countdownlatch一个功效了
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);//TODO ---每满50就开启一次，可以多次开启
        for (int i = 0; i < 10; i++) {//分6批，开始启动 300/50=6
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println("线程"+ finalI +"就绪");
                    cyclicBarrier.await();//TODO 等指定数量的线程（50个）处于等待状态时，继续执行后续代码
                    System.out.println(Thread.currentThread().getName()+" 运行了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }

    public static void main(String[] args) {
        testCyclicBarrier();
    }
}

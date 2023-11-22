package com.lixl.study.时间轮;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class HashedWheelTimerTest {
    public static void main(String[] args) throws InterruptedException {
        // HashedWheelTimer hashedWheelTimer = new HashedWheelTimer();
        //TODO 生成了一个时间轮，时间步长(tickDuration)为300ms
        // 意思是：每300毫秒跳一下，并激活对应格子里的任务
        // 有缺陷：如果调度的任务比较耗时，容易出现任务堆积
        // 结论：避免耗时比较长的任务
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(300,TimeUnit.MICROSECONDS);
        hashedWheelTimer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
//               执行耗时400毫秒的任务
                process(400);
            }
        }, 1000, TimeUnit.MICROSECONDS);

        hashedWheelTimer.stop();
    }

    /**
     * @param time 耗时
     */
    private static void process(int time) throws InterruptedException {
        System.out.println("处理业务逻辑");
        Thread.sleep(time);
    }
}

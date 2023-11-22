package com.lixl.study.thread.threadpool.mythreadPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MyFixedSizeThreadPool {


    private List<Thread> threadList;//这里的线程运送下面队列（仓库）中的任务（runnable）去cpu运行

    private BlockingQueue<Runnable> queue;


    private volatile boolean isWorking = true;
    public BlockingQueue<Runnable> getQueue() {
        return queue;
    }
    public boolean isWorking() {
        return isWorking;
    }
    /**
     * 构造函数
     *
     * @param poolsize     仓库的大小
     * @param threadNumber 创建线程的数量
     */
    public MyFixedSizeThreadPool(Integer poolsize, Integer threadNumber) {
        if (poolsize < 0 || threadNumber < 0) {
            throw new IllegalArgumentException("参数非法");
        }
        //初始化
        this.threadList = Collections.synchronizedList(new ArrayList<>());
        this.queue = new LinkedBlockingDeque(poolsize);//这么大小的一个队列
        for (int i = 0; i < threadNumber; i++) {
            MyWork myWork = new MyWork(this);
            myWork.start();
            threadList.add(myWork);
        }
    }
    //非阻塞的给仓库中添加任务
    public boolean execute(Runnable runnable) {
        if (this.isWorking) {
            boolean offer = this.queue.offer(runnable);
            return offer;
        } else {
            return false;
        }
    }
    //阻塞的给仓库中添加任务
    public void submit(Runnable runnable) throws InterruptedException {
        //先给仓库中添加任务
        this.queue.put(runnable);
    }
    public void shutDown() {
        this.isWorking = false;
        //则强行让之前已经在运行的线程结束
        for (Thread thread : threadList) {
            if (thread.getState().equals(Thread.State.BLOCKED)) {
                thread.interrupt();//中断线程
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {

        MyFixedSizeThreadPool myFixedThreadPool = new MyFixedSizeThreadPool(7, 4);

        //循环着给仓库（队列）中添加任务
        for (int i = 0; i < 10; i++) {
            //把线程池唤醒
            myFixedThreadPool.submit(() -> {
                System.out.println("任务被放入仓库：");
                try {
                    Thread.sleep(2000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("线程中断");
                }
            });
        }
        myFixedThreadPool.shutDown();
        System.out.println("_______________");

        //循环着给仓库（队列）中添加任务
        for (int i = 0; i < 3; i++) {
            //把线程池唤醒
            myFixedThreadPool.submit(() -> {
                System.out.println("任务被放入11111111仓库：");
                try {
                    Thread.sleep(2000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("线程中断");
                }
            });
        }




    }


}

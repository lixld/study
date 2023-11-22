package com.lixl.study.thread.threadpool.mythreadPool;


public class MyWork extends Thread {

    private MyFixedSizeThreadPool threadPool;

    public MyWork(MyFixedSizeThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        //threadPool.getQueue().size() > 0为了处理一种情况，关闭的时候仓库里的还有需要执行的任务
        while (threadPool.isWorking() || threadPool.getQueue().size() > 0) {
            try {
                Runnable task = null;
                    if (threadPool.isWorking()) {
                        //阻塞的方式去取拿客人，如果没有客人就一直等客人来
                        task = threadPool.getQueue().take();
                    } else {
                        task = threadPool.getQueue().poll();//非阻塞，取完就结束了。不在阻塞在这里
                    }
                if (task != null) {
                    task.run();
                    System.out.println("线程：" + Thread.currentThread().getName() + "执行完毕");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}

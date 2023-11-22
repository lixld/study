package com.lixl.study.thread.threadState;

public class ThreadStateDemo1_Sleep {

    static volatile boolean  running = true;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("sleep是线程Thread的静态方法" +
                "sleep不释放锁，不出让系统资源，线程仍然可以同步控制。" +
                "sleep会自动唤醒，如果时间不到，想要唤醒，可以使用interrupt()强行打断，" +
                "sleep可以用在任何地方" +
                "sleep需要捕获异常");

        Thread t1 = new Thread(()->{
            while (running){

            }
            System.out.println("t1 running is false, t1 will sleep");

            try {
                Thread.sleep(10000l);//此时设置这个线程为timed_waited状态
                System.out.println("模拟这个线程要运行10秒钟");
//                Thread.interrupted();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });


        System.out.println("new t1 后thread状态："+t1.getState());//NEW

        t1.start();

        Thread.sleep(2000l);

        System.out.println("start t1 后thread状态："+t1.getState());//RUNNABLE

        //控制标识设置为false,让子线程退出循环
        running = false;

        Thread.sleep(2000l);

        System.out.println("t1.sleep()时的状态："+t1.getState());//TIMED_WAITING
        System.out.println("打断线程：interrupt");
//        t1.interrupt();
        Thread.sleep(11000);
        System.out.println("t1执行完后的状态："+t1.getState());//TERMINATED



    }



}

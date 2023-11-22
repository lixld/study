package com.lixl.study.lock;

import java.io.IOException;

public class LockDemo1 {
    volatile int i = 0;
    DemoLock demoLock = new DemoLock();

    public void add() {
        demoLock.lock();//如果一个线程拿到锁，其它线程会等待
        try {
            i++;
        } finally {
            demoLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        LockDemo1 lockDemo1 = new LockDemo1();
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    lockDemo1.add();
                }
            }).start();
        }
        Thread.sleep(10000);
//        System.in.read();//输入任意键退出
        System.out.println(lockDemo1.i);
    }
}

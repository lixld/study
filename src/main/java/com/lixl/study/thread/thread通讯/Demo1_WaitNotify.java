package com.lixl.study.thread.thread通讯;

public class Demo1_WaitNotify {
    static volatile int i = 0;

    public static Object staticObject = new Object();
    public static void main(String[] args) {
        Demo1_WaitNotify demo1_waitNotify = new Demo1_WaitNotify();

        /**
         * 下面三个方法
         * 每个方法锁定的都是各自不同的对象
         *
         */
        /*
        test1锁定的是new 的一个对象
         */
        demo1_waitNotify.test1(new Object());
         /*
        test2锁定的是this对象,本质和test1中一样，也是方法内部唯一的这个this对象---其实就是调用它的（对象实例demo1_waitNotify）
         */
        demo1_waitNotify.test2();
        /*
        test3是静态方法，锁定的是全局的一个类变量staticObject，也是一个对象
         */
        test3();


    }

    private void test1(Object object) {
        new Thread(() -> {
            synchronized (object) {
                while (i < 10) {
                    System.out.println("t1:" + (++i));
                    object.notify();//此时自己还没有wait(),也就是唤醒其他人
                    try {
                        object.wait();//如果这里的wait和notify不能交换顺序，如果先wait,然后再notify（），则可能立刻把自己也马上重新唤醒了。你想想是不是这么理
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                object.notify();//这里很重要
            }
        }).start();

        new Thread(() -> {
            synchronized (object) {
                while (i < 10) {
                    System.out.println("  t2:" + (++i));
                    object.notify();
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                object.notify();
            }
        }).start();
    }
    public  void test2() {
        new Thread(() -> {
            synchronized (this) {
                while (i < 10) {
                    System.out.println("t1:" + (++i));
                    this.notify();//此时自己还没有wait(),也就是唤醒其他人
                    try {
                        this.wait();//如果这里的wait和notify不能交换顺序，如果先wait,然后再notify（），则可能立刻把自己也马上重新唤醒了。你想想是不是这么理
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.notify();//这里很重要
            }
        }).start();

        new Thread(() -> {
            synchronized (this) {
                while (i < 10) {
                    System.out.println("  t2:" + (++i));
                    this.notify();
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.notify();
            }
        }).start();
    }
    public static void test3() {
        new Thread(() -> {
            synchronized (staticObject) {
                while (i < 10) {
                    System.out.println("t1:" + (++i));
                    staticObject.notify();//此时自己还没有wait(),也就是唤醒其他人
                    try {
                        staticObject.wait();//如果这里的wait和notify不能交换顺序，如果先wait,然后再notify（），则可能立刻把自己也马上重新唤醒了。你想想是不是这么理
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                staticObject.notify();//这里很重要
            }
        }).start();

        new Thread(() -> {
            synchronized (staticObject) {
                while (i < 10) {
                    System.out.println("  t2:" + (++i));
                    staticObject.notify();
                    try {
                        staticObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                staticObject.notify();
            }
        }).start();
    }

}

package com.lixl.study.时间轮;

public class MyThread extends Thread{

    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.run();
    }
    @Override
    public void run(){
        System.out.println("1111");
    }
}

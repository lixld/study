package com.lixl.study.designPattern.action.templete.demo2_callback.hook;

public class ShutdownHookDemo {
    private static class ShutdownHook extends Thread {
        public void run() {
            System.out.println("I am called during shutting down.");
        }
    }

//    public static void main(String[] args) {
//        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
//    }
}

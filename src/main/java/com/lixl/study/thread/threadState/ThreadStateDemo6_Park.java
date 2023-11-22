package com.lixl.study.thread.threadState;

import java.util.concurrent.locks.LockSupport;

public class ThreadStateDemo6_Park {
    public static void main(String[] args) throws InterruptedException {

        Thread t1= new Thread(()->{
            LockSupport.park();
        });
        t1.start();
        System.out.println("---------："+t1.getState());//WAITING
        Thread.sleep(2000l);

        System.out.println("t1被park后的状态："+t1.getState());//WAITING

        LockSupport.unpark(t1);

        System.out.println("t1被unpark后的状态："+t1.getState());//WAITING
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName());
        LockSupport.unpark(thread);
        System.out.println("a");
        LockSupport.park();
        System.out.println("b");
//        LockSupport.park();
//        System.out.println("c");
    }

    //二叉树遍历
    public void per(Node node){
        System.out.println(node.data);
        //baseCase
        Node left = node.left;
        per(left);
        Node right = node.right;
        per(right);
    }
    public void mid(Node node){
        //baseCase
        Node left = node.left;
        mid(left);
        System.out.println(node.data);
        Node right = node.right;
        mid(right);
    }

    public void post(Node node){
        //baseCase
        Node left = node.left;
        post(left);
        Node right = node.right;
        post(right) ;
        System.out.println(node.data);
    }

    private class Node {
        private Object data;
        private Node left;
        private Node right;
    }
}

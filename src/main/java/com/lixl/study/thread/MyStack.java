package com.lixl.study.thread;

import java.util.concurrent.atomic.AtomicReference;

public class MyStack {

    AtomicReference<Node> top = new AtomicReference<>();
int v;
    //出栈 --把链表最顶部的取走
    public Node pop(){
        Node oldTop = top.get();
        Node nextNode = oldTop.nextNode;
        top.compareAndSet(oldTop,nextNode);
        return oldTop;
    }

    //入栈，把它放到链表的顶部
    public void push(Node newTop){
        Node oldTop = top.get();
        newTop.nextNode = oldTop;
        oldTop.preNode = newTop;
        top.compareAndSet(oldTop,newTop);
    }

    class Node{
        public String value;
        public Node preNode;
        public Node nextNode;
    }
}

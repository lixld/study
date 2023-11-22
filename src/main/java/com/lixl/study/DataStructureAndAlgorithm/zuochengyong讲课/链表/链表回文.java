package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.链表;

import java.util.Stack;

public class 链表回文 {
    public static class Node{
        private Integer value;
        private Node next;
    }

    //借助栈 实现回文判定
    public boolean isPalindrome(Node head){
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur.next!=null){
            cur = cur.next;
            stack.push(cur);
        }


        Node pop = stack.pop();
        while (pop!=null){
            pop = stack.pop();
        }

        return true;
    }
}

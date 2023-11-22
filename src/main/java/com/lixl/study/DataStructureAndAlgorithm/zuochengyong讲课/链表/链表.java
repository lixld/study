package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.链表;

/**
 * 快慢指针
 */
public class 链表 {
    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            this.value = v;
        }
    }

    /**
     * @param head 头
     * @return
     */
    public static Node midOrUpMidNode(Node head) {
        //如果少于三个点，则直接返回head
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        //链表有3个点或以上
        Node slow = head.next;
        Node fast = head.next.next;
        //想象脑海中画面，两个指针同时往前跳，只是步长不同
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static Node midOrUpMidPreNode(Node head) {
        //如果少于三个点，则直接返回head
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        //链表有3个点或以上
        Node slow = head;
        Node fast = head.next.next;
        //想象脑海中画面，两个指针同时往前跳，只是步长不同
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


    /**
     * @param head 头
     * @return
     */
    public static Node midOrDownMidNode(Node head) {
        //如果少于三个点，则直接返回head
        if (head == null || head.next == null) {
            return head;
        }
        //链表有2个点或以上
        Node slow = head.next;
        Node fast = head.next;
        //想象脑海中画面，两个指针同时往前跳，只是步长不同
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


    /**
     * @param head 头
     * @return
     */
    public static Node midOrDownMidPreNode(Node head) {
        //如果少于三个点，则直接返回head
        if (head == null || head.next == null) {
            return null;
        }
        if (head.next.next == null) {
            return head;
        }
        //链表有2个点或以上
        Node slow = head;
        Node fast = head.next;
        //想象脑海中画面，两个指针同时往前跳，只是步长不同
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }





}

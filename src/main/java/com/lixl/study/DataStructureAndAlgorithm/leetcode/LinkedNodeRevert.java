package com.lixl.study.DataStructureAndAlgorithm.leetcode;

import java.util.Stack;

import static com.lixl.study.DataStructureAndAlgorithm.leetcode.LinkedNode.initLink;
import static com.lixl.study.DataStructureAndAlgorithm.leetcode.LinkedNode.soutLinkedNode;

public class LinkedNodeRevert {


    //4种方法
    //①头插法

    /**
     * 1-->2-->3-->4-->5
     * <p>
     * 1<--2<--3<--4<--5
     *
     * @param head
     * @return
     */
    public static LinkedNode headInsert(LinkedNode head) {

        /**
         * 分两部分处理
         * 1.队首元素
         * 2.其余元素
         */
        LinkedNode tail = head;//先定义一个last表示反转后的第一个节点(原先的第一个，就是未来的最后一个)

        // 2.其余元素
        LinkedNode cur = head.next;//当前位置在第2个元素

        LinkedNode temp;
        while (cur != null) {
            temp = cur;      //临时保存当前位置---到temp指针
            cur = cur.next;  //元素向右→移动
            temp.next = head;//临时位置的下一个位置（temp.next）指向对头
            head = temp;     //新对头（也就原先的下一个元素）指向temp

        }

        tail.next = null;
        return head;


    }


    //②入栈法
    public static LinkedNode stackInsert(LinkedNode head) {
        Stack<Integer> stack = new Stack();
        while (head != null) {
//            int value = head.value;
//            stack.push(value);
            head = head.next;
        }

        LinkedNode sentinal = new LinkedNode();//专门有个指针--->来指向对头，它的作用就是哨兵站岗（啥都不做）
        LinkedNode tempNode = sentinal;
        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            LinkedNode newNode = new LinkedNode(pop);
            tempNode.next = newNode;
            tempNode = tempNode.next;
        }
        return sentinal.next;
    }

    //③三指针法
    public static LinkedNode threeIndex(LinkedNode head) {

        LinkedNode pre = null;
        LinkedNode cur = head;
        LinkedNode curNext = null;

        LinkedNode newHead = null;//用来返回的头结点

        while (cur != null) {
            //1.先明确下一个指针，指向哪里
            curNext = cur.next;
            if (curNext == null) {//表明cur已经是最后一个节点
                newHead = cur;
            }
            cur.next = pre;
            pre = cur;
            cur = curNext;

        }
        return newHead;

    }

    //④递归方式
    public static LinkedNode recursion(LinkedNode head) {
        //①终止条件
        if (head == null || head.next == null) {//head.next这里为了处理链表，只有一个元素的情形
            return head;
        }
        LinkedNode newHead = recursion(head.next);//先遍历到最后一个元素，然后从他开始反转

        //②BaseCase
        // [最小问题解]--脑子中想象，链表只有3个元素，只需把头和尾部换一下就行，下面公式就出来了
        head.next.next = head;//把原先尾部指向头部
        head.next = null;//把原先头指向尾部

        return newHead;

    }


    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};

        LinkedNode linkedNode = initLink(nums);
        soutLinkedNode(linkedNode);

//        LinkedNode linkedNode1 = reverse(linkedNode);
//        LinkedNode linkedNode1 = reverse2(linkedNode);
//        LinkedNode linkedNode1 = reverse3(linkedNode);
        LinkedNode linkedNode1 = recursion(linkedNode);
        soutLinkedNode(linkedNode1);


    }
}

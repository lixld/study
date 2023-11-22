package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树.遍历;

import java.util.LinkedList;
import java.util.Queue;

public class 树_层遍历 {

    class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 借助数据结构：：：：：：队列
     *
     * 考题1：二叉树的横向遍历
     * 口诀：--准备队列（只有头结点）--死盯着这个队列，只要队列不为空就执行下面逻辑列表
     *
     * ①出队-弹出即打印  （当前节点为弹出节点）
     *
     * ②入队-当前节点 有左-则左入队  + 当前节点 有右-则右入队
     *
     * @param head
     */
    //按层遍历  借助一个队列即可
    public void level(Node head) {
        if (head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            //出队
            Node pop = queue.poll();
            System.out.println(pop.value);//弹出即打印

            //入队
            Node left = pop.left;
            if (left != null) {
                queue.add(left);
            }

            Node right = pop.right;
            if (right != null) {
                queue.add(right);
            }

        }
    }
}

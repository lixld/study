package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树.遍历;

import java.util.Stack;

public class 树_上下遍历_非递归 {

    class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     *
     * 借助数据结构：：：：栈
     *
     * 遍历（非递归实现）
     * 类似公理，记住就好了，
     * 【记住口诀，多练习】
     */


    /**
     * 先序  头--左--右
     * 记口诀：
     * while循环（不为空）--始终盯着这个栈，--这个很重要
     * ①弹出即打印
     * ②
     * 如有右，压入右
     * 如有左，压入左
     *
     * @param head
     */
    public void pre(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<Node>();
        stack.push(head);

        while (!stack.isEmpty()) {//这个很重要，我们一直盯着这个栈来操作
            //①
            head = stack.pop();
            System.out.println(head.value);

            //②
            Node right = head.right;
            if (right != null) {
                stack.push(right);
            }
            Node left = head.left;
            if (left != null) {
                stack.push(left);
            }
        }
    }

    /**
     * 后 序 头--右--左  ===》 然后倒序 就是 左--右--头 【这就是后序遍历了】
     * 记口诀：
     * while循环（不为空）--始终盯着这个栈，--这个很重要
     * ①弹出 不打印--放入额外栈
     * ②---注意这里和前面有区别，顺序变了【理由是后续是先处理右，所以需要左先入栈，出栈顺序才能保证右先被处理】
     * 如有左，压入左
     * 如有右，压入右
     * <p>
     * ③打印额外栈
     *
     * @param head
     */
    public void pos(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> s2 = new Stack<>();//额外准备的栈：用来存数据，结束后遍历该栈即可


        Stack<Node> s1 = new Stack<>();
        s1.add(head);
        while (!s1.isEmpty()) {

            head = s1.pop();//弹出，先不打印，而是直接放入s2中
            s2.push(head);

            Node left = head.left;
            if (left != null) {
                s1.push(left);
            }

            Node right = head.right;
            if (right != null) {
                s1.push(right);
            }
        }

        //遍历结果栈，输出结果
        while (!s2.isEmpty()) {
            System.out.println(s2.pop());
        }
    }


    /**
     * 中序
     * 记口诀：
     * ①整条左边界 依次压入栈中（不停替换head位置-到它的左指针位置）
     * ② 条件①无法继续，弹出打印，同时（不停替换head位置，到它的右指针位置）
     *
     * @param head
     */
    public void in(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<Node>();
        while (!stack.isEmpty() || head != null) {
            //优先逻辑1（左边界入栈）
            if (head != null) { //这里只负责入
                stack.push(head);//指针左移
                head = head.left;//很可能head此时就是null了，这样就进入了下面的逻辑
            } else {//逻辑1不满足，则进入逻辑2（弹出并打印，同时把头节点指向它的右指针位置）
                head = stack.pop(); //这里只负责出
                System.out.println(head.value);
                head = head.right;//指针右移
            }
        }
    }

}

package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树.遍历;

public class 树_上下遍历_递归 {

    class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }
    //遍历（递归实现）

    //先序
    public void pre(Node head) {
        if (head == null) {
            return;
        }
        System.out.println(head.value);
        pre(head.left);
        pre(head.right);
    }

    //中序
    public void in(Node head) {
        if (head == null) {
            return;
        }
        pre(head.left);
        System.out.println(head.value);
        pre(head.right);
    }

    //后续
    public void pos(Node head) {
        if (head == null) {
            return;
        }
        pre(head.left);
        pre(head.right);
        System.out.println(head.value);
    }

    /**
     * 总结：递归序
     *
     * 每个点都会被经过3次（也就是到达3次）
     *
     *
     */
    public void f(Node head) {
        if (head == null) {
            return;
        }
        //1  这里打印是前序
        f(head.left);
        //2 这里打印是中序
        f(head.right);
        //3 这里打印是后序
    }


    //非遍历（递归实现）


}

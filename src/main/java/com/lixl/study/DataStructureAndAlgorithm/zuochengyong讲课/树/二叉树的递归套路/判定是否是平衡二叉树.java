package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树.二叉树的递归套路;

/**
 * @author lixinlin
 * @date 2023/2/13
 * 平衡二叉树定义：左右子树都是平衡二叉树，并且整颗二叉树高度最大差1层
 */
public class 判定是否是平衡二叉树 {

    class Node {
        public Object value;
        public Node left;
        public Node right;

    }

    public static class Info {
        private boolean isBalance;
        private Integer height;

        public Info(boolean isBalance, Integer height) {
            this.isBalance = isBalance;
            this.height = height;
        }
    }

    /**
     * 问题转化为：
     * 把某个节点的数据，组装成封装类的结构
     *
     * @param x
     * @return
     */
    public static Info process2(Node x) {

        if (x == null) {
            return new Info(true, 0);
        }

        Info left = process2(x.left);
        Info right = process2(x.right);

        int xHeight = Math.max(left.height, right.height) + 1;

        boolean xBalance = false;
        if (left.isBalance && right.isBalance && Math.abs(left.height - right.height) <= 1) {
            xBalance = true;
        }
        Info info = new Info(xBalance, xHeight);
        return info;
    }

    public static boolean isBalanced2(Node head) {
        return process2(head).isBalance;

    }


}

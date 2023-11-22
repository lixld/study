package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树.二叉树的递归套路;

/**
 * 求整颗树的的最大距离
 *
 * @author lixinlin
 * @date 2023/2/14
 */
public class 二叉树最大距离 {
    private static class Node {
        private Integer value;
        private Node left;
        private Node right;
    }

    static class Info {
        private int maxDistance;
        public int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    public static Info process(Node x) {
        if (x == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(x.left);

        Info rightInfo = process(x.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        int maxDistance = Math.max(Math.max(leftInfo.maxDistance, rightInfo.maxDistance), leftInfo.height + rightInfo.height + 1);

        return new Info(maxDistance, height);
    }

    /**
     * 主函数
     *
     * @param node
     * @return
     */
    public static int maxDistance2(Node node) {
        return process(node).maxDistance;
    }
}

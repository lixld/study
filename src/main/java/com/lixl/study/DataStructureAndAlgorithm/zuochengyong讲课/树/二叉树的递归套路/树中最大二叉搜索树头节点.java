package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树.二叉树的递归套路;

/**
 * @author lixinlin
 * @date 2023/2/14
 */
public class 树中最大二叉搜索树头节点 {

    class Node {
        private Integer value;
        private Node left;
        private Node right;
    }

    class Info {
        private boolean isAllBST;
        private Integer maxSubBSTSize;//最大搜索二叉树节点数量
        private Integer max;//最大值
        private Integer min;//最小值

        public Info(boolean isAllBST, Integer maxSubBSTSize, Integer max, Integer min) {
            this.isAllBST = isAllBST;
            this.maxSubBSTSize = maxSubBSTSize;
            this.max = max;
            this.min = min;
        }
    }

    public Info process(Node x) {
        if (x == null) {
            return null;
        }

        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        int max1 = x.value;//最大值
        int min1 = x.value;//最小
        boolean isAllBst1 = false; //是否二叉搜索树
        int maxSubBSTSize1 = 0; //二叉搜索子树的最大节点数量

        //最大值
        if (leftInfo != null) {
            max1 = Math.max(leftInfo.max, x.value);
            min1 = Math.min(leftInfo.min, x.value);
        }
        if (rightInfo != null) {
            max1 = Math.max(rightInfo.max, x.value);
            min1 = Math.max(rightInfo.min, x.value);
        }

        //最大节点数量
        maxSubBSTSize1 = Math.max(leftInfo.maxSubBSTSize, rightInfo.maxSubBSTSize) + 1;

        //可能性① 与x无关 ，就先用着
        if (leftInfo != null) {
            maxSubBSTSize1 = leftInfo.maxSubBSTSize;
        }
        if (rightInfo != null) {//找到更好的答案
            maxSubBSTSize1 = Math.max(maxSubBSTSize1, rightInfo.maxSubBSTSize);
        }

        //可能性②、maxSubBSTSize = 以x为头节点的所有节点数
        if (
                (leftInfo == null ? true : leftInfo.isAllBST)//左树整体是搜索二叉树
                        &&
                        (rightInfo == null ? true : rightInfo.isAllBST)//右树整体是搜索二叉树
                        &&
                        (leftInfo == null ? true : x.value > leftInfo.max)
                        &&
                        (rightInfo == null ? true : x.value < rightInfo.min)
        ) {//是二叉搜索树
            isAllBst1 = true;
            //以x为头节点的所有节点数
            maxSubBSTSize1 = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize)
                    + 1;
        }
        Info info = new Info(isAllBst1, max1, min1, maxSubBSTSize1);
        return info;
    }
}

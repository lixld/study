package com.lixl.study.DataStructureAndAlgorithm.dp;

/**
 * 01背包问题
 * 两个数组
 * 重量数组 w[]
 * 价值数组 v[]
 * 背包容量 bag
 * <p>
 * 求排列组合的最大价值 是多少？
 */
public class PackageProblem {
    /**
     * 不超重情况下的最大价值
     *
     * @param w
     * @param v
     * @param bag
     * @return
     */
    public int pageckMaxValue(int[] w, int[] v, int bag) {
        //异常情况
        if (w == null || v == null || w.length != v.length || bag <= 0) {
            return 0;
        }
        int result = process(w, v, 0, bag);
        return result;
    }

    //当前考虑到了index号货物，index...所有货物可以自由选择
    //做的选择不能超过背包容量
    //返回最大价值
    public int process(int[] W, int[] V, int index, int bag) {
        if (index == W.length) {
            return 0;
        }
        if (bag < 0) {
            return -1;
        }
        //考虑下一件
        //不要
        int p1 = process(W, V, index++, bag);
        //要
        int p2 = process(W, V, index++, bag - W[index]);
        if (p2 != -1) {
            p2 += V[index];
        }
        return Math.max(p1, p2);
    }
}

package com.lixl.study.DataStructureAndAlgorithm.dp;

/**
 * 数组的每个下标作为一个阶梯，第 i 个阶梯对应着一个非负数的体力花费值 cost[i]（下标从 0 开始）。支付相应的体力值。
 * 你可以爬一个阶梯或者两个阶梯，求达到顶部的最小花费，
 * 在开始时，你可以选择从下标为 0 或 1 的元素作为初始阶梯。
 * <p>
 * 输入：cost = [10, 15, 20]
 * 输出：15
 * 解释：最低花费是从 cost[1] 开始，然后走两步即可到阶梯顶，一共花费 15 。
 * <p>
 * 输入：cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
 * 输出：6
 * 解释：最低花费方式是从 cost[0] 开始，逐个经过那些 1 ，跳过 cost[3] ，一共花费 6 。
 * <p>
 * 状态转移方程 dp[i] = min(dp[i-1],dp[i-2])+cost[i];
 */
public class 最小花费爬楼梯 {
    public int minCost(int[] cost) {//台阶数花费精力数

        int length = cost.length;

        int[] dp = new int[length];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < length; i++) {
            dp[i] = Math.min(cost[i - 1], cost[i - 2]) + cost[i];
        }
        //最后一步不需要花费，取倒数第一步和倒数第二步的最小值
        int min = Math.min(dp[length - 1], dp[length - 2]);
        return min;

    }
}

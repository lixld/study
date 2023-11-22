package com.lixl.study.DataStructureAndAlgorithm.leetcode;

/**
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * <p>
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * <p>
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：[7,1,5,3,6,4]
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
 * <p>
 * <p>
 * 示例 2：
 * <p>
 * <p>
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
 * <p>
 * <p>
 * <p>
 * <p>
 * 提示：
 * <p>
 * <p>
 * 1 <= prices.length <= 10⁵
 * 0 <= prices[i] <= 10⁴
 * <p>
 * <p>
 * Related Topics 数组 动态规划 👍 2477 👎 0
 */

//股票买卖
public class ArraySharesUnload {

    public static int maxProfit(int[] shares) {
        int maxProfit = 0;
        if (shares.length <= 2) {
            if (shares[1] - shares[0] > 0) {
                return shares[1] - shares[0];
            }
            return maxProfit;
        }
        int buyDay = 0;
        int saleDay = 0;
//        输入：[7,1,5,3,6,4]
        for (int i = shares.length - 1; i >= shares.length - 2; i--) {
            for (int j = shares.length - 2; j >= 0; j--) {
                int i1 = shares[i] - shares[j];
                if (i1 > maxProfit) {
                    maxProfit = i1;
                    saleDay = i;
                    buyDay = j;
                }
            }
        }
        System.out.println("buyDay:" + (buyDay+1) + "---saleDay:" + (saleDay+1));
        return maxProfit;
    }

    public static void main(String[] args) {
        int[] shares = {8, 10};
        int i = maxProfit(shares);
        System.out.println(i);
    }

}

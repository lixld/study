package com.lixl.study.DataStructureAndAlgorithm.dp;

/**
 * 有 N 阶楼梯，每次可以上一阶或者两阶，求有多少种上楼梯的方法。
 */
public class 爬楼梯 {

    //暴力递归解法
    public static int methodNumber(int N) {
        if (N == 1) return 1;
        if (N == 2) return 2;
        int i = methodNumber(N - 1) + methodNumber(N - 2);
        return i;
    }

    //动规解法
    public static int methodNumber_dp(int N) {
        int[] result = new int[N + 1];
        result[1] = 1;
        result[2] = 2;
        for (int i = 3; i < N; i++) {
            result[i] = result[i - 1] + result[i - 2];
        }

        return result[N];
    }

    public static int methodNumber_dp_kongjiang(int N) {
        int pre1 = 1, pre2 = 2;

        for (int i = 3; i < N; i++) {
            int cur = pre1 + pre2;
            pre1 = pre2;
            pre2 = cur;
        }
        return pre2;
    }

    public static void main(String[] args) {
        int i = methodNumber_dp(9);
        System.out.println(i);
    }

}

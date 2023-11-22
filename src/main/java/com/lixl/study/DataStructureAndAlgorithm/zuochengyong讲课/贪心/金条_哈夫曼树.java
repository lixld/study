package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.贪心;

import java.util.PriorityQueue;

/**
 * @author lixinglin
 * @date 2023/3/15
 */
public class 金条_哈夫曼树 {

    public static int process(int[] arr, int pre) {
        if (arr.length == 1)
            return pre;

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {

            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i]));
            }

        }
        return ans;
    }

    private static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];

        int ansi = 0;

        for (int arri = 0; arri < arr.length; arri++) {

            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return new int[0];
    }

    public static int lessMoney2(int[] arr) {

        PriorityQueue<Integer> pQ = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            pQ.add(arr[i]);
        }
        int sum = 0;
        while (pQ.size() > 1) {
            int i = pQ.poll() + pQ.poll();
            sum += i;
            pQ.add(i);
        }
        return sum;

    }
}

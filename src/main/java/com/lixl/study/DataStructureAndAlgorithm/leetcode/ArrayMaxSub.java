package com.lixl.study.DataStructureAndAlgorithm.leetcode;

public class ArrayMaxSub {
    //思路：暴力解法，双循环 计算，结果临时记录。并替换
    public static int maxSubArray(int[] nums) {
        int result = 0;
        int from = 0, to = 0;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int num1 = nums[j];
                num += num1;
                if (num > result) {
                    result = num;
                    from = i;
                    to = j;
                }
            }
        }
        System.out.println("开始下标：" + from + "结束下标:" + to);
        return result;
    }

    public static void main(String[] args) {
        /**
         * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
         * 输出：6
         * 解释：连续子数组[4,-1,2,1] 的和最大，为6 。
         */
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int i = maxSubArray(nums);
        System.out.println(i);
    }

}

package com.lixl.study.DataStructureAndAlgorithm.leetcode;

public class ArrayTwoSum {

    public static int[] twoSum(int[] nums, int target) {
        int firstIndex = 0;
        int secondIndex = 0;
        boolean isbreak = false;
        for (int i = 0; i < nums.length; i++) {
            if (isbreak) {
                break;
            }
            int first = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int second = nums[j];
                if (first + second == target) {
                    firstIndex = i;
                    secondIndex = j;
                    isbreak = true;
                    break;
                }
            }

        }
        int[] restult = new int[2];
        if (isbreak) {
            restult[0] = firstIndex;
            restult[1] = secondIndex;
        }
        return restult;
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 22;
        int[] ints = twoSum(nums, target);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }


    }
}

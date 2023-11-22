package com.lixl.study.DataStructureAndAlgorithm.leetcode;

import java.util.Arrays;

/**
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * <p>
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * <p>
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元
 * 素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * 输出：[1,2,2,3,5,6]
 * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
 * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
 * <p>
 * <p>
 * 示例 2：
 * <p>
 * <p>
 * 输入：nums1 = [1], m = 1, nums2 = [], n = 0
 * 输出：[1]
 * 解释：需要合并 [1] 和 [] 。
 * 合并结果是 [1] 。
 * <p>
 * <p>
 * 示例 3：
 * <p>
 * <p>
 * 输入：nums1 = [0], m = 0, nums2 = [1], n = 1
 * 输出：[1]
 * 解释：需要合并的数组是 [] 和 [1] 。
 * 合并结果是 [1] 。
 * 注意，因为 m = 0 ，所以 nums1 中没有元素。nums1 中仅存的 0 仅仅是为了确保合并结果可以顺利存放到 nums1 中。
 * <p>
 * <p>
 * <p>
 * <p>
 * 提示：
 * <p>
 * <p>
 * nums1.length == m + n
 * nums2.length == n
 * 0 <= m, n <= 200
 * 1 <= m + n <= 200
 * -10⁹ <= nums1[i], nums2[j] <= 10⁹
 * <p>
 * <p>
 * <p>
 * <p>
 * 进阶：你可以设计实现一个时间复杂度为 O(m + n) 的算法解决此问题吗？
 * <p>
 * Related Topics 数组 双指针 排序 👍 1529 👎 0
 */
public class ArrayMergeSort {
    public static void arrayMerge(int[] num1, int m, int[] num2, int n) {

        int[] num3 = Arrays.copyOf(num1, m);
        num1 = new int[m + n];

        int i = 0;
        int j = 0;
        int k = 0;
        while (k < m + n) {
            if (i < m && j < n) {
                if (num3[i] <= num2[j]) {
                    num1[k++] = num3[i++];//移动num1指针
                } else {
                    num1[k++] = num2[j++];//移动num2指针
                }
            } else {
                if (i > m) {//一直是num2中数据小，遍历的是它，num2已经遍历完了---则剩下的就是把num1（数据保存在mum3中）中剩余的拼接上
                    num1[k++] = num2[j++];
                } else
                    num1[k++] = num3[i++];
            }
        }
        System.out.println(num1);
    }

    public static void main(String[] args) {
        int[] num1 = {1, 2, 4, 6, 7};
        int[] num2 = {2, 3, 4, 5};
        arrayMerge(num1, 5, num2, 4);

    }
}

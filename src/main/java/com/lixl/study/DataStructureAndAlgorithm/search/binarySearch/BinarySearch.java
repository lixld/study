package com.lixl.study.DataStructureAndAlgorithm.search.binarySearch;

/**
 * 折半查找
 */
public class BinarySearch {


    /**
     * @param a //数组必须是有序的
     * @param n
     * @param value
     *
     * low、high、mid 都是指数组下标
     * 其中 low 和 high 表示当前查找的区间范围，初始 low=0， high=n-1。mid 表示[low, high]的中间位置
     * 我们通过对比 a[mid]与 value 的大小，来更新接下来要查找的区间范围，直到找到或者区间缩小为 0，就退出
     *
     * @return
     */
    public int bsearch(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;

        while (low <= high) { //易出错点1：
            int mid = (low + high) / 2;
            if (a[mid] == value) {
                return mid;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }
}

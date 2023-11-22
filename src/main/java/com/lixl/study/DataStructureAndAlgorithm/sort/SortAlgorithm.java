package com.lixl.study.DataStructureAndAlgorithm.sort;

import java.util.Arrays;

/**
 * https://time.geekbang.org/column/article/41913?utm_campaign=guanwang&utm_source=baidu-ad&utm_medium=ppzq-pc&utm_content=title&utm_term=baidu-ad-ppzq-title
 *
 * @author : lixl
 * @DOC:https://mp.weixin.qq.com/s/HQg3BzzQfJXcWyltsgOfCQ
 * @date : 2020-08-24 16:15:59
 **/
public class SortAlgorithm {
    /**
     * 冒泡排序
     *
     * @param a 数组
     */
    public static void bubbleSort(int[] a) {
        int n = a.length;
        if (n <= 1) {
            return;
        }
        for (int i = 0; i < n; i++) {
            //提前退出循环的标志位
            boolean flag = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {//交换
                    int temp;
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    flag = true; //表示有数据交换
                }
            }
            if (!flag) break;
        }
    }

    /**
     * 插入算法的核心思想是:
     * 取未排序区间中的元素,在已排序区间中找到合适的插入位置将其插入,
     * 并保证已排序区间数据一直有序。
     * 重复这个过程,直到未排序区间中元素为空,算法结束。
     * <p>
     * 插入排序有两个动作：
     * 1.比较
     * 2.移动
     *
     * @param a
     */
    // 插入排序,a表示数组,n表示数组大小
    public static void insertionSort(int[] a) {
        int n = a.length;
        if (n <= 1) return;
        for (int i = 1; i < n; ++i) {//当前处理到外面数组的第几个元素
            int value = a[i];//开始要比较的元素
            int j = i - 1;//倒叙着 和它前面那个 开始比较
            // 查找插入的位置
            for (; j >= 0; j--) {//前面的有序数组
                if (a[j] > value) {//如果它的数据比当前选出来的大,则往后挪窝
                    a[j + 1] = a[j]; // 数据移动
                } else {
                    break;
                }
            }
            //找到了当前选出来的数据的位置,给他安顿好位置
            a[j + 1] = value; // 插入数据
        }
    }

    public static void selecterSort(int[] a) {
        for (int i = 0; i < a.length; i++) {

            int minIndex = i;
            for (int j = i; j < a.length; j++) {//右边的无序数组
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = a[minIndex];
            a[minIndex] = a[i];
            a[i] = temp;
        }
    }


    /**
     * 归并排序
     * 用递归实现
     *
     * @param array
     */
    public static int[] mergeSort(int[] array) {
        if (array.length < 2) return array;
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        return merge(mergeSort(left), mergeSort(right));
    }

    /**
     * 此时left和right已是有序数组
     *
     * @param left
     * @param right
     * @return
     */
    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0;
        for (int index = 0; index < result.length; index++) {
            //处理其余数据
            if (i >= left.length) {//说明左边处理完了,只剩下右边
                result[index] = right[j++];
            } else if (j >= right.length) {
                result[index] = left[i++];
            } else if (left[i] <= right[j]) {
                result[index] = left[i++];
            } else {
                result[index] = right[j++];
            }
        }
        return result;
    }


    /**
     * @param array 待排序总数组
     * @param start
     * @param end
     * @return
     */
    public static int[] quickSort(int[] array, int start, int end) {
        if (array.length < 1 || start < 0 || end >= array.length || start > end) return null;
        int smallIndex = partition(array, start, end);//分区动作,找到分区节点
        if (smallIndex > start) {
            quickSort(array, start, smallIndex - 1);
        }
        if (smallIndex < end) {
            quickSort(array, smallIndex + 1, end);
        }
        return array;
    }

    /**
     * 该函数做了两件事：
     * * 1.找到分区位置下标 （把比pivot小的放左边,比pivot大的放右边--此时pivot就是那中间值了）
     * * 2.在找的同时做交换操作-整理数据
     *
     * @param array 待排序总数组
     * @param start 未处理区间的开始坐标（从0-（strart-1)是已处理区间））
     * @param end   未处理区间的结束坐标
     * @return
     */
    public static int partition(int[] array, int start, int end) {
        /**
         * 下面两个方法是等价的
         */
//        int pivot = (int) (start + Math.random() * (end - start + 1));
//        swap(array, pivot, end);
        int pivot = array[end];//基准pivot(默认值为最后一个值),其实用上面的随机方法更好一些
        int smallIndex = start - 1;//--中间值坐标(默认值为开始的第一个元素)--也就是已排序的末尾元素坐标
        for (int i = start; i <= end; i++) {//遍历未处理区间
            if (array[i] <= pivot) {//以最后一个值作为基准pivot,如果比它小--则下标右移
                smallIndex++;//如果找到,则下标右移
                if (i > smallIndex) {//说明需要交换
                    swap(array, i, smallIndex);
                }
            }
        }
        return smallIndex;
    }


    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    /**
     * @param array
     * @param bucketSize 桶的个数
     * @return
     */
    public static int[] bucketSort(int[] array, int bucketSize) {
        int[] result = new int[array.length];
        int resultIndex = 0;
        if (array == null || array.length < 2) return array;
        int max = array[0];
        int min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        int bucketCount = (max - min) / bucketSize;//--每个桶里存放的元素数量
        bucketCount = bucketCount + 1;
        for (int i = 0; i < bucketSize; i++) {//遍历每个桶
            int[] bucketArr = new int[bucketCount];
            int bucketArrIndex = 0;
            int bucketStart = min + i * bucketCount;
            int bucketEnd = bucketStart + bucketCount - 1;
            for (int j = 0; j < array.length; j++) {
                int arrayElement = array[j];
                //把数据安放到合适区间
                if (arrayElement >= bucketStart && arrayElement <= bucketEnd) {
                    bucketArr[bucketArrIndex++] = arrayElement;
                }
            }
            //桶内排序--计数排序就是省去了这个环节，每个桶里都是放着一样的数据
            bucketArr = quickSort(bucketArr, 0, bucketArr.length - 1);
            for (int j = 0; j < bucketArr.length; j++) {
                int i1 = bucketArr[j];
                if (i1 != 0) {
                    result[resultIndex++] = i1;
                }
            }
        }
        return result;
    }



    public static void main(String[] args) {
        int[] a = {4, 2, 5, 12, 3};
//        bubbleSort(a);
//        insertionSort(a);
//        selecterSort(a);
//        a = mergeSort(a);
//        a = quickSort(a, 0, a.length - 1);
//        a = bucketSort(a,3);
        int[] b = {22, 5, 11, 41, 45, 26, 29, 10, 7, 8, 30, 27, 42, 43, 40};
        bucketSort(b, 5);
//        System.out.println(Arrays.toString(a));
    }
}

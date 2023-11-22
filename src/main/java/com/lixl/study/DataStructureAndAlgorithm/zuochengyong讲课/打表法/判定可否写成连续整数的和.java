package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.打表法;

/**
 * @author lixinlin
 * @date 2023/2/21
 */
public class 判定可否写成连续整数的和 {

    public static boolean test(int num) {

        for (int i = 1; i < num; i++) {
            int sum = i;
            for (int j = i + 1; j < num; j++) {
                sum += j;
                if (sum > num) {
                    break;
                }
                if (sum == num)
                    return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            System.out.println(i + "----" + test(i));
        }
    }

    /**
     * 这种题目说服他：
     * 找个切口：暴力解给出答案，然后找规律，
     * 至于内部原理，则不需要花太多时间探究，
     * 就好像1+1为啥=2 难道我们还要去想想为什么么？
     * @param x
     * @return
     */
    public static boolean improve(int x) {
        /**
         * num&(num-1)==0   是2的某次方
         * num&(num-1)！=0 不是2的某次方
         *
         * num-1 如果
         *
         * 前面的特殊处理：
         * 后面的统一规律：
         *  2 的n次方都是false 其余都是true
         *   4 8 16 32
         */
        if (x == 1 || x == 2) {
            return false;
        }
        if ((x & 1) == 0) return false;
        return true;


    }
}

package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.矩阵;

/**
 * @author lixinglin
 * @date 2023/3/1
 */
public class 转圈打印 {
    /**
     * @param m 数组
     * @param a a行(起始位置)
     * @param b b列(起始位置)
     * @param c c行(结束位置)
     * @param d d列(结束位置)
     */
    public static void printEdge(int[][] m, int a, int b, int c, int d) {
        /**
         * 特殊情况处理
         * 1.只剩一行
         * 2.只剩一列
         */
        if (a == c) {
            while (b <= d) {
                System.out.println(m[a][b++]);
            }
        } else if (b == d) {
            while (c >= a) {
                System.out.println(m[c--][b]);
            }
        } else {
            /**
             * 正常情况
             */
            int curC = b;
            int curR = a;
            while (curC != d) {
                System.out.println(m[a][curC++]);
            }
            while (curR != c) {
                System.out.println(m[curR++][curC]);
            }
            while (curC != b) {
                System.out.println(m[curR][curC--]);
            }
            while (curR != a) {
                System.out.println(m[curR--][curC]);
            }
        }
    }


    public static void spiralOrderPrint(int[][] maxtrix) {
        int tR = 0;
        int tC = 0;
        int dR = maxtrix.length - 1;
        int dC = maxtrix[0].length - 1;
        while (tR <= dR && tC <= dC) {//不越界就一直打印
            printEdge(maxtrix, tR++, tC++, dR--, dC--);
        }
    }
}

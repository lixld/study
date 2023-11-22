package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.矩阵;

/**
 * @author lixinglin
 * @date 2023/3/1
 */
public class 原地旋转正方形矩阵 {
    /**
     * @param m 数组
     * @param a a行(起始位置)
     * @param b b列(起始位置)
     * @param c c行(结束位置)
     * @param d d列(结束位置)
     */
    public static void rotateEdge(int[][] m, int a, int b, int c, int d) {

        //有多少组
        int gropNumber = d - b;
        int tmp = 0;
        for (int i = 0; i < gropNumber; i++) {//变量所有组
            tmp = m[a][b + i];
            //组内第一个元素
            //int first = m[a][b + i];
            //int second = m[a + i][d];
            //int third = m[c][d - i];
            //int four = m[c - i][d];

            m[a][b + i] = m[c - i][d];//第一个位置==》值

            m[a + i][d] = m[a][b + i];//第二个位置==》值

            m[c][d - i] = m[a + i][d];//第三个位置==》值

            m[c - i][d] = tmp;        //第四个位置==》值


        }


    }


    public static void spiralOrderPrint(int[][] maxtrix) {
        int a = 0;
        int b = 0;
        int c = maxtrix.length - 1;
        int d = maxtrix[0].length - 1;
        while (a < c) {//不越界就一直打印
            rotateEdge(maxtrix, a++, b++, c--, d--);
        }
    }

}

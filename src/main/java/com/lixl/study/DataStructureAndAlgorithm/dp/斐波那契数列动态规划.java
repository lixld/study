package com.lixl.study.DataStructureAndAlgorithm.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * 斐波那契数列
 */
public class 斐波那契数列动态规划 {

    //递归解法
    public static int f(int N) {
        if (N == 1) {
            return 1;
        }
        if (N == 2) {
            return 1;
        }
        int i = f(N - 1) + f(N - 2);
        return i;
    }


    //分治法


    //暴力递归--->傻缓存--->动态规划解法
    static Map<Integer, Integer> map = new HashMap();//这里就是空间「根据特点，可以使map,也可以数组（一维或二维）」

    public static int f1(int N) {
        if (N == 1) {
            return 1;
        }
        if (N == 2) {
            return 1;
        }
        Integer o1 = map.get(N - 1);
        Integer o2 = map.get(N - 2);
        int i = 0;
        if (o1 == null && o2 == null) {
            i = f(N - 1) + f(N - 2);
        } else if (o1 != null && o2 == null) {
            i = o1 + f(N - 2);
        } else if (o1 == null && o2 != null) {
            i = f(N - 1) + o2;
        } else if (o1 != null && o2 != null) {
            i = o1 + o2;
        }
        map.put(N, i);
        return i;

    }

    //动态规划 --一个数组即可

    public static int f2(int N) {
        //①初始化准备静态数据
        int[] F = new int[N + 1];//默认赋值为0
        F[1] = 1;
        F[2] = 1;
        for (int i = 3; i < N + 1; i++) {
            F[i] = F[i - 1] + F[i - 2];
        }

        //②直接返回结果
        return F[N];
    }

    /**
     * 1 2 3 4 5 6  7  8  9 10  11
     * 1 1 2 3 5 8 13 21 34 55  89
     *
     * @param args
     */
    public static void main(String[] args) {
        int f = f1(10);
        System.out.println(f);
    }
}

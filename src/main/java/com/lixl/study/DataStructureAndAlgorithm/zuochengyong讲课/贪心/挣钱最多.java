package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.贪心;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author lixinglin
 * @date 2023/3/15
 */
public class 挣钱最多 {

    @Data
    @AllArgsConstructor
    static class Program {
        private int p;
        private int c;
    }

    /**
     * 根据花费组织的小根堆 比较器
     */
    @Data
    static class MinCostComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.c - o2.c;
        }
    }


    /**
     * 根据利润组织的大根堆 比较器
     */
    @Data
    static class MaxProfitComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o2.p - o1.p;
        }
    }


    /**
     * @param k       项目
     * @param w       初始资金
     * @param Profits 花费
     * @param Capital 收益
     * @return
     */
    public static int findMaximizedCapital(int k, int w, int[] Profits, int[] Capital) {
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());

        for (int i = 0; i < Profits.length; i++) {

            minCostQ.add(new Program(Profits[i], Capital[i]));
        }
        for (int i = 0; i < k; i++) {
            while (!minCostQ.isEmpty() && minCostQ.poll().c <= w) {//被我的本金cover住了
                maxProfitQ.add(minCostQ.poll());
            }
            if (maxProfitQ.isEmpty()) {
                return w;
            }
            w += maxProfitQ.poll().p;
        }
        return w;
    }


}

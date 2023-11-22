package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树;

/**
 * @author lixinlin
 * @date 2023/2/13
 */
public class 折纸条凹凸折痕 {

    public static void printAllFolds(int N) {
        printProcess(1, N, true);
    }

    /**
     * 递归过程来到某个点
     *
     * @param i    节点所在层
     * @param N    一共的层数
     * @param down = true 凹  =false 凸
     */
    public static void printProcess(int i, int N, boolean down) {
        if (i > N) {
            return;
        }

        printProcess(i + 1, N, true);
        System.out.println(down ? "凹" : "凸");
        printProcess(i + 1, N, false);

    }

    public static void main(String[] args) {
        printAllFolds(3);
    }

}

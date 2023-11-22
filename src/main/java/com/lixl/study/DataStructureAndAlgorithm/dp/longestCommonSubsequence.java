package com.lixl.study.DataStructureAndAlgorithm.dp;

public class longestCommonSubsequence {

    public static int longestCommonSubsequence1(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        //尝试
        Integer process = process(str1, str2, str1.length - 1, str2.length - 1);
        return process;
    }

    /**
     * 从右往左比较
     *
     * @param str1 [0...i]  12d3
     * @param str2 [0...j]  a123  最长公共子序列多长？
     * @param i    -(index)串1的下标index
     * @param j    -(index)串2的下标index--都是从末尾最后一位开始比较
     * @return
     */
    private static int process(char[] str1, char[] str2, int i, int j) {
        //想边界
        char str1ichar = str1[i];
        char str2jchar = str2[j];
//        str1[0...i=0] str2[0...j=0]
        if (i == 0 && j == 0) {//特殊情况处理,index=0（两个串都只有一位）
            int i1 = str1ichar == str2jchar ? 1 : 0;
            return i1;
        } else if (i == 0) {//特殊情况处理，str1[0...i=0](只有串1是一位，串2>1)
            //如果串2的末尾正好相等，则找到，直接返回
            if (str1ichar == str2jchar) {
                return 1;
            } else {//如果串2的末尾不等，则继续往前找，找串2的倒数第二位(也就是j-1)
                return process(str1, str2, i, j - 1);
            }
        } else if (j == 0) {//特殊情况处理，只有串2是一位
            //如果串1的末尾正好相等，则找到，直接返回
            if (str1ichar == str2jchar) {
                return 1;
            } else {//如果串1的末尾不等，则继续往前找，找倒数第二位(也就是i-1)
                return process(str1, str2, i - 1, j);
            }
        } else { //最普通情况 两个都不止一位  i!=0 &&j!=0
            /**
             * 样本对应模型（经验）
             * 几种可能得情况：
             * ①不考虑i j可能考虑，有可能不考虑   a123d
             * ②不考虑j i可能考虑，也可能不考虑
             */
            //串1的i!=串2的j
            //前进方式1  串1移位 串2不动
            int p1 = process(str1, str2, i - 1, j);
            //前进方式2  串1不动 串2移位
            int p2 = process(str1, str2, i, j - 1);
            //串1的i==串2的j,则继续找下一个相等的...(他们俩都移位到上一个字符)
            //串1的i！=串2的j 返回0
            int p3 = str1ichar == str2jchar ? (1 + process(str1, str2, i - 1, j - 1)) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

//    m*n的表就把所有数字拿下了！

    /**
     * 从右往左比较
     *
     * @param s1 [0...i]  a12c3d
     * @param s2 [0...j]  efg123xy
     * @return
     */
    public static int longestCommonSubsequence2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1charArray = s1.toCharArray();
        char[] str2charArray = s2.toCharArray();
        int N = str1charArray.length;
        int M = str2charArray.length;
        int[][] dp = new int[N][M];

        dp[0][0] = str1charArray[0] == str2charArray[0] ? 1 : 0;

        for (int j = 1; j < M; j++) {
            dp[0][j] = str1charArray[0] == str2charArray[j] ? 1 : dp[0][j - 1]/*process(str1charArray, str1charArray, i, j)*/;
        }
        for (int i = 1; i < N; i++) {
            dp[i][0] = str1charArray[i] == str2charArray[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                int p1 = dp[i - 1][j];
                int p2 = dp[i][j - 1];
                int p3 = str1charArray[i] == str2charArray[j] ? 1 + dp[i - 1][j - 1] : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[N - 1][M - 1];

    }

}

package com.lixl.study.DataStructureAndAlgorithm.dp;

/**
 * 先手，后手问题
 *
 * 绝顶聪明的两个人 从一个数组中取值，
 * 每次只能从最左边/最右边取值 取一个值
 * 问:获胜者能取到的最大分数是多少？？？
 */
public class FirstAndSecond {
    //暴力递归 recrusion

    /**
     * 先手，从数组L~R中取值
     * @param arr
     * @param L   下标
     * @param R   下标
     * @return
     */
    public int first(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }
        int second = arr[L] + second(arr, L + 1, R);
        int second1 = arr[R] + second(arr, L, R - 1);
        int max = Math.max(second, second1);
        return max;
    }

    /**
     * 后手，从数组L~R中取值
     * @param arr
     * @param L   下标
     * @param R   下标
     * @return
     */
    public int second(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int first = first(arr, L + 1, R);//对手拿走L位置的数
        int first1 = first(arr, L, R - 1);//对手拿走R位置的数
        int min = Math.min(first, first1);
        return min;
    }

    public int winerScore(int[] arr) {
        int first = first(arr, 0, arr.length - 1);
        int second = second(arr, 0, arr.length - 1);
        int max = Math.max(first, second);
        return max;
    }


    /**
     * @param arr
     * @param L   下标
     * @param R   下标
     * @return
     */
    //傻缓存
    public int first1(int[] arr, int L, int R, int[][] firstDp, int[][] secondDP) {
        int i = firstDp[L][R];
        int answer;
        if (i != -1) {
            return i;
        }
        if (L == R) {
            answer = arr[L];
        } else {

            int second = arr[L] + second1(arr, L + 1, R, firstDp, secondDP);
            int second1 = arr[R] + second1(arr, L, R - 1, firstDp, secondDP);
            int max = Math.max(second, second1);
            answer = max;
        }
        firstDp[L][R] = answer;
        return answer;
    }

    /**
     * @param arr
     * @param L   下标
     * @param R   下标
     * @return
     */
    public int second1(int[] arr, int L, int R, int[][] firstDp, int[][] secondDP) {
        int i = secondDP[L][R];
        int answer;
        if (i != -1) {
            return i;
        }
        if (L == R) {
            answer = 0;
        } else {
            int first = first1(arr, L + 1, R, firstDp, secondDP);//对手拿走L位置的数
            int first1 = first1(arr, L, R - 1, firstDp, secondDP);//对手拿走R位置的数
            int min = Math.min(first, first1);
            answer = min;
        }
        secondDP[L][R] = answer;
        return answer;
    }

    public int winerScore1(int[] arr) {
        int N = arr.length;
        int[][] firstDp = new int[N][N];
        int[][] secondDP = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                firstDp[i][j] = -1;
                secondDP[i][j] = -1;
            }
        }
        int first = first1(arr, 0, arr.length - 1, firstDp, secondDP);
        int second = second1(arr, 0, arr.length - 1, firstDp, secondDP);
        int max = Math.max(first, second);
        return max;
    }

    //动态规划
    public int winerScore2(int[] arr) {
        int N = arr.length;
        int[][] firstDp = new int[N][N];
        int[][] secondDP = new int[N][N];


        for (int i = 0; i < N; i++) {
            firstDp[i][i] = arr[i];

        }

        for (int startCol = 1; startCol < N; startCol++) {
            int L = 0;
            int R = startCol;
            while (R < N) {

                int second = arr[L] + secondDP[L +1][R];
                int second1 = arr[R] + secondDP[L][R-1];
                firstDp[R][L] = Math.max(second, second1);
                int first = firstDp[L + 1][R];
                int first1 = firstDp[L][R - 1];
                secondDP[R][L] = Math.min(first, first1);

                L++;
                R++;
            }
        }

        int max = Math.max(firstDp[0][N - 1], secondDP[0][N - 1]);
        return max;
    }

}

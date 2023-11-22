package com.lixl.study.DataStructureAndAlgorithm.dp;

public class RobotWalk {
    public static void valid(int N, int start, int k, int aim) {
        if (N < 1 || start < 0 || start > N || k < 0 || aim < 0 || aim > N) {
            throw new RuntimeException("数据非法");
        }
    }

    /**
     * @param N    一共多少位置
     * @param sart 开始位置
     * @param aim  目标位置
     * @param k    可以走多少步
     * @return
     */
    public static int way1(int N, int sart, int k, int aim) {
        valid(N, sart, k, aim);
        int process = way1_process(N, sart, k, aim);
        return process;
    }

    /**
     * 递归方法:
     *
     * @param cur  机器人当前位置
     * @param rest 机器人还有rest步去走
     *             -------------------下面这两个参数，是无论如何都不会变的（因为目标始终是aim，总共的位置也始终是N个)）----------------
     * @param aim  最终的目标是aim
     * @param N    有哪些位置？1~N
     * @return （）在一共有N个位置的前提下）机器人从cur出，走过rest步后，到达aim的方法数是多少？？
     */
    public static int way1_process(int N, int cur, int rest, int aim) {
        if (rest == 0) {//如果已经不需要走了，走完了！
            return cur == aim ? 1 : 0;
        }
        //rest>0，还有步数要走
        //(cur,rest)
        if (cur == 1) {//只能往右
            return way1_process(2, rest - 1, aim, N);
        }
        //(cur,rest)
        if (cur == N) {//只能往左
            return way1_process(N - 1, rest - 1, aim, N);
        }
        //(cur,rest)
        //可以往左也可以往右
        int right = way1_process(cur + 1, rest - 1, aim, N);
        int left = way1_process(cur - 1, rest - 1, aim, N);
        return left + right;
    }


    /**
     * 傻缓存法
     * 记忆化搜索-（从顶向下的动态规划--还不是彻底的动态规划，因为动态规划根本不考虑题目本身是啥意思，只是关注空间【数组】中填充的规律是啥)）
     *
     * @param N     总共多少数
     * @param start 开始时所在的位置
     * @param aim   要到达的目标位置
     * @param k     需要走的步数
     * @return
     */
    public static int way2(int N, int start, int k, int aim) {
        valid(N, start, k, aim);
        int[][] dp = new int[N + 1][k + 1];
        //N+1 * K+1
        //初始化
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = -1;//
            }
        }


        //dp就是缓存表
        //process1（cur,rest)
        //之前没算过！ dp[cur][rest]=-1;
        //如果算过！返回值， dp[cur][rest]
        //N+1 * k+1

        int process = way2_process(N, start, k, aim, dp);
        return process;
    }


    /**
     * @param cur  当前位置
     * @param rest 还剩多少步
     * @param aim
     * @param N
     * @param dp   --带着缓存玩
     * @return
     */
    public static int way2_process(int N, int cur, int rest, int aim, int[][] dp) {
        int element = dp[cur][rest];
        if (element != -1) {
            return element;
        }

        //region 这里和process1是一样的方法
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        int answer;
        //rest>0
        if (cur == 1) {//只能往右
            answer = way2_process(2, rest - 1, aim, N, dp);
        } else if (cur == N) {//只能往左
            answer = way2_process(N - 1, rest - 1, aim, N, dp);
        } else {
            //可以往左也可以往右
            int right = way2_process(cur + 1, rest - 1, aim, N, dp);
            int left = way2_process(cur - 1, rest - 1, aim, N, dp);
            answer = left + right;
        }
        //endregion


        //区别只是在这里 给缓存赋值
        dp[cur][rest] = answer;

        return answer;
    }


    /**
     * * 真正的动态规划  只关注空间（二位数组本身，以及它的填充方法【这个方法】)）
     * * 详解看process地址 https://www.processon.com/diagraming/6399856b0e3e746a2da699ea
     *
     * @param N     总共多少数
     * @param start 开始时所在的位置
     * @param aim   要到达的目标位置
     * @param K     需要走的步数
     * @return
     */
    public static int way3(int N, int start, int K, int aim) {
        valid(N, start, K, aim);
        //step1:初始化 填充二维数组
        //step1-1  先定义二维数组的大小（边界）
        int[][] dp = new int[N + 1][K + 1];
        //N+1 * K+1

        //第一列
        dp[aim][0] = 1;//dp[....][0]= 0
        for (int rest = 1; rest <= K; rest++) {//从第2列开始
            dp[1][rest] = dp[2][rest - 1];//第一行【从下标为1开始填充，这样自然就把每一列的第一个数据跳过去了】
            for (int cur = 2; cur < N; cur++) {//从第2行开始
                dp[rest][cur] = dp[cur + 1][rest - 1] + dp[cur - 1][rest - 1];
            }
            dp[N][rest] = dp[N - 1][rest - 1];//最后一行
        }


        //step2:直接取值
        return dp[start][K];
    }

    public static void main(String[] args) {

    }

}

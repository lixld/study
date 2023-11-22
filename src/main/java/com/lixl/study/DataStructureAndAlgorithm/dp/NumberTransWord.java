package com.lixl.study.DataStructureAndAlgorithm.dp;

/**
 * 规定如下：
 * 1-A
 * 2-B
 * 3-C...
 * 26-Z
 * 那么一个数字字符串可以有多种转化
 * 111-AAA
 * 111-KA
 * 111-AK..
 * 那么给定一个只有数字字符组成的字符串str,返回有多少种转化结果？？
 */
public class NumberTransWord {

    public static int transNumber(String arr) {
        return process(arr.toCharArray(), 0);
    }

    /**
     * @param str[0...i-1]转化无需过问
     * @param str[i...]去转化，返回有多少种转化方法
     * @return
     */
    public static int process(char[] str, int i) {
        if (i == str.length) {
            return 1;
        }
        //这里显然是错的，说明之前做过的决定有问题！
        //比如：305就不能转
        if (str[i] == '0') {
            return 0;
        }
        //str[i] !='0'
        //可能性1：i单转
        int ways = process(str, i + 1);

        //可能性2：i和i+1字符共同构成的转--特殊条件除外 二者的和大于26不能转
        if (i + 1 < str.length && (str[i] - '0') * 10 + (str[i + 1] - '0') <= 26) {
            ways += process(str, i + 2);
        }
        return ways;
    }

    public static int dp(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        //为啥一维表？--因为一个可变参数代表一切
        int[] dp = new int[N + 1];
        //看位置依赖，任何一个位置进来，都依赖他后面的位置----毫无疑问，从右往左填，填到0位置停
        dp[N] = 1;
        for (int i = N - 1; i > 0; i--) {
            //这里显然是错的，说明之前做过的决定有问题！
            //比如：305就不能转
            if (str[i] != '0') {
                //str[i] !='0'
                //可能性1：i单转
//                int ways = process(str, i + 1);
                int ways = dp[i + 1];

                //可能性2：i和i+1字符共同构成的转--特殊条件除外 二者的和大于26不能转
                if (i + 1 < str.length && (str[i] - '0') * 10 + (str[i + 1] - '0') <= 26) {
                    ways += dp[i + 2];
                }
                dp[i] = ways;
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        transNumber("335345567891");
        dp("335345567891");
    }
}

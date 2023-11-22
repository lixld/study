package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.打表法;

/**
 * @author lixinlin
 * @date 2023/2/17
 */
public class 先手后手吃草问题 {

    /**
     * 前置条件：
     * 1.先手后手都绝顶聪明
     * 2.每次只能吃4的n次方的草量
     * <p>
     * 谁最先吃完，谁获胜
     *
     * @param n n份草放在一堆
     * @return 先手、 后手
     */
    public static String process(int n) {

        //baseCase
        //0   1   2  3  4
        //后  先  后  先 先
        if (n == 0) return "后手";
        if (n == 1) return "先手";
        if (n == 2) return "后手";
        if (n == 3) return "先手";
        if (n == 4) return "先手";


        int base = 1;//先手决定吃的草数
        //当前是先手在选
        while (base < n) {//把能试的情况，都试一遍
            //当前一共n份草， 先手吃掉base份，后给后手n-base份
            if (process(n - base) == "后手") {//主过程中是先手，那么在子过程中，则先手变成了后手（如果我（后手）能赢，那么我就赢了）
                return "先手";//那么我就赢了
            }
            if (base * 4 > n)//防止溢出
                break;
            base *= 4;
        }//每种情况都试了，依然赢不了，那么只能是后手赢了
        return "后手";

    }

    public static void main(String[] args) {
        for (int i = 1; i <= 50; i++) {
            System.out.println("草数量：" + i + "    赢家：" + process(i));
        }
    }

    public String improveWinner(int caoshu) {
        return caoshu % 5 == 0 || caoshu % 5 == 2 ? "后手" : "先手";
    }

}

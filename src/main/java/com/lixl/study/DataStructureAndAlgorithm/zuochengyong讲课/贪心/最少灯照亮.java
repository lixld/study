package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.贪心;

import java.util.HashSet;

/**
 * @author lixinglin
 * @date 2023/3/2
 */
public class 最少灯照亮 {


    public static int minLight1(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    /**
     * @param str
     * @param index  index之前的已经安排好了 index以及之后的所有位置可以安排
     * @param lights 已经放的位置坐标，存在lights里
     *               <p>
     *               要求选出能照亮所有.的方案， 并且在这些方案中，选择使用灯数量最少的方案 中灯的数量
     * @return
     */
    public static int process(char[] str, int index, HashSet<Integer> lights) {

        if (index == str.length) {//结束，一定到最后才能结算得结论

            //验证所有节点，方案有效性（方案能否把所有点照亮？）
            for (int i = 0; i < str.length; i++) {
                if (str[i] != 'X') {//当前位置是点的位置
                    if (!(lights.contains(i - 1) || lights.contains(i) || lights.contains(i + 1))) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        } else {//str还没结束
            // index位置不论是X还是.都可以选择当前位置是否放灯
            int no = process(str, index + 1, lights);//当前位置(index) 没有放灯【因为lights没有动】，你给我返回最好结果


            int yes = Integer.MAX_VALUE;// 当前位置(index) 放灯。 只有位置是.才可以放灯！只有位置是. yes才有可能被设置，否则不可能被设置
            if (str[index] == '.') {//做出放灯的决定
                lights.add(index);//放灯
                yes = process(str, index + 1, lights);
                lights.remove(index);//恢复现场，因为一直用的是同一个lights 并没有拷贝新的
            }
            return Math.min(no, yes);

        }

    }


    /**
     * 每次都用最优的决策
     * @param road
     * @return
     */
    public static int minLight(String road) {
        char[] str = road.toCharArray();
        int index = 0;
        int light = 0;
        while (index < str.length) {

            if (str[index] == 'X') {

                index++;
            } else {
                if (index + 1 == str.length) {
                    break;
                } else {

                    light++;
                    if (str[index + 1] == 'X') {
                        index = index + 2;
                    } else {
                        index = index + 3;
                    }
                }
            }
        }
        return light;
    }



}

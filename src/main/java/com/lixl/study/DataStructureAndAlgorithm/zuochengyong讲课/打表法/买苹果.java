package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.打表法;

/**
 * @author lixinlin
 * @date 2023/2/17
 */
public class 买苹果 {
    //暴力解法
    //6号袋和8号袋   优先用8号袋，不行再选择6号袋试

    public static int maxDaiziNumber(int appleNumber) {

        /**
         * 逻辑清晰
         * 1. 奇数直接返回
         * 2. 少于6个直接返回
         * 3. 6和8做特殊处理返回1
         * 4.其余的普适情况
         *    a.不需要6号袋参与优先8个袋，能搞定直接返回
         *    b.  需要6号袋参与  结论（其实就是循环的跳出条件）
         *      b1 循环外：先看剩余是否能被6号袋消化，如果能，则直接消化返回，如果不能才需要进入循环去不停减8号袋
         *      b2 循环内：
         *          b21.搞定   （8号袋先不减，看剩余能否被6号袋消化。如果消化，则直接返回，如果不能消化，则进入循环8号袋一直递减，某一次的剩余数量 被6整除了）
         *          b22.搞不定   （8号袋一直递减，知道减少为0，剩余还是不能被6整除）
         */
        if ((appleNumber & 1) == 1) return -1;
        if (appleNumber < 6) return -1;
        if (appleNumber == 6) return 1;
        if (appleNumber == 8) return 1;

        int shengyuNumber = appleNumber % 8;
        int eightBagNumber = appleNumber / 8;
        if (shengyuNumber == 0) {
            return eightBagNumber;
        }
        //先看剩余是否能被6号袋消化，如果能，则直接消化返回，如果不能才需要进入循环去不停减8号袋
        int sixBagNumber = shengyuNumber / 6;
        shengyuNumber = shengyuNumber % 6;
        if (shengyuNumber == 0) {
            return eightBagNumber + sixBagNumber;
        }

        while (true) {


            eightBagNumber--;
            shengyuNumber = appleNumber - eightBagNumber * 8;

            sixBagNumber = shengyuNumber / 6;
            shengyuNumber = shengyuNumber % 6;

            if (shengyuNumber == 0) {
                return eightBagNumber + sixBagNumber;
            }
            if (eightBagNumber == 0 && shengyuNumber != 0)
                return -1;

        }


    }


    /**
     * 打表法
     *
     * @param appNumber
     * @return
     */
    public int improvement(int appNumber) {
        if ((appNumber & 1) == 1) return -1;
        if (appNumber == 6 || appNumber == 8) return 1;
        if (appNumber == 12 || appNumber == 14 || appNumber == 16) return 2;
        if (appNumber <= 18) {
            return -1;
        }

        return (appNumber - 18) / 8 + 3;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {

            int result = maxDaiziNumber(i);
            System.out.println(i + "个苹果-->" + "需要最少袋子数量：" + result);

        }


    }
}

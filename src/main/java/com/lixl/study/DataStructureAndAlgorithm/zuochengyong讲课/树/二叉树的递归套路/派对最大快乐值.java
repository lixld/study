package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树.二叉树的递归套路;

import java.util.List;

/**
 * @author lixinlin
 * @date 2023/2/14
 */
public class 派对最大快乐值 {

    class Info {
        private int yes;//来参加的--最大happy值
        private int no;//不来参加--最大happy值
        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }


    class Employ {
        private int happy;
        private List<Employ> nexts;
    }

    public Info process(Employ X) {

        if (X.nexts.isEmpty()) {
            return new Info(X.happy, 0);
        }

        //
        int yes = X.happy;
        int no = 0;
        List<Employ> nexts = X.nexts;
        for (Employ next : nexts) {
            Info nexInfo = process(next);//获取到用户 来的最大值|不来的最大值
            yes += nexInfo.no;//情况①x来 的情况下  派对最大快乐值  [x来，他的下属就不能来了]，所以要取下属用户----  不来的happy值 求和
            no += Math.max(nexInfo.yes, nexInfo.no); //情况②x不来的情况下，派对最大快乐值【x不来，则他的下属，可来可不来，（快乐值取来/不来 中的较大值）】
        }

        return new Info(yes, no);
    }

    //for test
    //public static Employ genarateBoass(int maxLevel,int maxNexts,int maxHppy){
    //    if (Math.random()<0.02) return null;
    //
    //
    //}

}

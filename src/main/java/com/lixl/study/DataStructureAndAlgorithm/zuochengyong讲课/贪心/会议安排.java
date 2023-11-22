package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.贪心;

import lombok.Data;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author lixinglin
 * @date 2023/3/2
 * <p>
 * 要求安排最多场次的会议
 */
public class 会议安排 {
    public class Program {
        public int start;
        public int end;
    }
    /**
     * 目前来到timeline时间点，已经安排了done场会议，剩余自由安排
     *
     * @param programs 还剩多少会议
     * @param done     之前已经安排过的数量
     * @param timeline 目前来到的时间点
     */
    public static int process(Program[] programs, int done, int timeline) {
        if (programs.length == 0) return done;
        int max = 0;
        //当前安排的会议是什么会，每一个都枚举
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start > timeline) {//可以安排
                Program[] next = copyButExcept(programs, i);
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max;
    }

    /**
     * 把所有会议copy一份，并把当前会议删除
     *
     * @param programs
     * @param i
     * @return
     */
    private static Program[] copyButExcept(Program[] programs, int i) {

        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }
    @Data
    public static class ProgramCompare implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }
    public static int bestArrange2(Program[] programs) {
        Arrays.sort(programs, new ProgramCompare());//根据结束时间早排序
        int result = 0;
        int timeline = 0;
        for (int i = 0; i < programs.length; i++) {

            Program program = programs[i];
            if (timeline < program.start) {//可以安排
                timeline = program.end;
                result++;
            }
        }
        return result;


    }


}

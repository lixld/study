package com.lixl.study.DataStructureAndAlgorithm.dp;

import java.util.HashMap;

/**
 * --------贴纸游戏----------
 * <p>
 * 字符串 str
 * <p>
 * 字符串数组 arr(都是小写英文，每个元素代表一张贴纸)--
 * 可以把单个字符剪开使用，目的是拼出str
 * 返回需要至少多少张贴纸可以完成任务？
 * <p>
 * str = "babac";
 * arr = {"ba","c","abcd"}
 * 至少需要两张贴纸"ba"和"abcd"（或者两个abcd）
 * 因为使用这两张贴纸，把每个字符单独剪开，含有2个a,2个b,1个c
 * 是可拼出str的，所以返回2
 */
public class Tiezhi {
    /**
     * 减函数
     * source距离target还差多少？
     */
    private static String minus(String target, String source) {
        char[] str1 = target.toCharArray();
        char[] str2 = source.toCharArray();
        int[] count = new int[26];//26个英文字符，每个字母出现的次数
        for (char cha : str1) {
            count[cha - 'a']++;//下标位置元素出现次数++
        }
        for (char cha : str2) {
            count[cha - 'a']--;//下标位置元素出现次数--
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    stringBuilder.append((char) (i + 'a'));
                }
            }
        }
        return stringBuilder.toString();
    }
    //所有贴纸sticker，每一种贴纸都有无穷张
    //target
    //最少张数
    public static int process1(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String first : stickers) {//从第一张开始遍历，（因为第一张总归会是这些中的某一张）
            String rest = minus(target, first);
            //终止条件：最后剩余需要匹配的字符串 和目标字符串一样长，
            // 比如剩余 wmt ,目标是：amu,此时代表是最后一次机会，
            // 如果该次没有成功则就没结果--也就终止了，不再匹配了！
            if (rest.length() != target.length()) {
                //min重新赋值 ，源头一直没变（strickers），只是目标变了
                min = Math.min(min, process1(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }
    public static int minStickers1(String[] stickers, String target) {
        int ans = process1(stickers, target);
        ans = ans == Integer.MAX_VALUE ? -1 : ans;
        return ans;
    }

    /**
     * 元数据数组（也就是所有纸牌组成的二维数组）
     * @param stickers
     * @return
     */
    private static int[][] initSource2Array(String[] stickers) {
        int N = stickers.length;
        int[][] counts = new int[N][26];//因为定位了是26个位置，并且默认值都是0
        for (int i = 0; i < N; i++) {
            String source = stickers[i];
            char[] chars = source.toCharArray();
            for (char aChar : chars) {
                /**
                 * 比如 'a'-'a' = 0 定位下标index = 0
                 * 比如 'b'-'a' = 1 定位下标index = 1
                 */
                int index = aChar - 'a';//字符串中字母---对应在26个英文字母中的下标
                counts[i][index]++;//值负责填充（或者重置）对应位置下标的值
            }
        }
        return counts;
    }
    /**
     * 比如： abaadbom
     * 3 2 0 1
     * [a,b,c,d...]
     * 0 1 2 3 ...
     * @param t
     * @return
     */
    private static int[] initTarget2Array(String t) {
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for (char targetChar : target) {
            /**
             * 比如 'a'-'a' = 0 定位下标index = 0
             * 比如 'b'-'a' = 1 定位下标index = 1
             */
            int index = targetChar - 'a';//字符串中字母---对应在26个英文字母中的下标
            tcounts[index]++;//没有++的默认赋值是0，所以后面要做判断>0才去做操作
        }
        return tcounts;
    }
    /**
     * @param stickers source的二维数组---所有贴纸
     * @param t        目标
     * @return
     */
    public static int process2(int[][] stickers, String t) {
        if (t.length() == 0) {
            return 0;
        }
        char[] target = t.toCharArray();
        int[] tcounts = initTarget2Array(t);//target词频统计
        int min = Integer.MAX_VALUE;
        int N = stickers.length;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];//尝试第一张贴纸是谁
            //最关键优化（重要的剪枝，这一步也是贪心）
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    char c = (char) (j + 'a');//把对应位置--属于26个字母中的哪个定位
                    int sourceNumber = sticker[j];//源中 26英文字母中每个字母对应位置的字母个数
                    int tcount = tcounts[j];//目标中 26英文字母中每个字母对应位置的字母个数
                    if (tcount > 0) {//对于没有赋值的默认值为0，这种不需要判定
                        int nums = tcount - sourceNumber;//目标-源 还差的个数
                        for (int k = 0; k < nums; k++) {//差的字母个数（有几个就append几个）这里不用担心负数，因为for循环中自动会帮助做过滤
                            stringBuilder.append(c);
                        }
                    }
                }
                String rest = stringBuilder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    public static int process3(int[][] stickers, String t, HashMap<String,Integer> dp) {
        if (dp.containsKey(t)){
            Integer integer = dp.get(t);
            return integer;
        }
        if (t.length() == 0) {
            return 0;
        }
        char[] target = t.toCharArray();
        int[] tcounts = initTarget2Array(t);//target词频统计
        int min = Integer.MAX_VALUE;
        int N = stickers.length;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];//尝试第一张贴纸是谁
            //最关键优化（重要的剪枝，这一步也是贪心）
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    char c = (char) (j + 'a');//把对应位置--属于26个字母中的哪个定位
                    int sourceNumber = sticker[j];//源中 26英文字母中每个字母对应位置的字母个数
                    int tcount = tcounts[j];//目标中 26英文字母中每个字母对应位置的字母个数
                    if (tcount > 0) {//对于没有赋值的默认值为0，这种不需要判定
                        int nums = tcount - sourceNumber;//目标-源 还差的个数
                        for (int k = 0; k < nums; k++) {//差的字母个数（有几个就append几个）这里不用担心负数，因为for循环中自动会帮助做过滤
                            stringBuilder.append(c);
                        }
                    }
                }
                String rest = stringBuilder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(t,ans);
        return ans;
    }

    public static int minStickers2(String[] stickers, String target) {

        //stickers组装2维数组
        int[][] sourceArray = initSource2Array(stickers);
        int ans = process2(sourceArray, target);
        ans = ans == Integer.MAX_VALUE ? -1 : ans;
        return ans;
    }

    public static int minStickers3(String[] stickers, String target) {
        //stickers组装2维数组
        int[][] sourceArray = initSource2Array(stickers);
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("",0);
        int ans = process3(sourceArray, target, dp);
        ans = ans == Integer.MAX_VALUE ? -1 : ans;
        return ans;
    }

    public static void main(String[] args) {
        String[] strings = {"", ""};
        String target = "";
        int v1_result = minStickers1(strings, target);
        int v2_result = minStickers2(strings, target);
        int v3_result = minStickers3(strings, target);
        System.out.println();
    }
}

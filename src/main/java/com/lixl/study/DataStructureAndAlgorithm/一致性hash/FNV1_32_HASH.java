package com.lixl.study.DataStructureAndAlgorithm.一致性hash;

//不同的hash算法 ---体现价值 ---有些算法生成的比较散列
public class FNV1_32_HASH {
    public static int getHash(String content) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < content.length(); i++) {
            hash = (hash ^ content.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        //如果计算出来的结果为负数，则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }
}

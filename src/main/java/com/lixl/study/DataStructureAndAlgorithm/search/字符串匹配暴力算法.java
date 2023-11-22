package com.lixl.study.DataStructureAndAlgorithm.search;

class 字符串匹配暴力算法 {
    public static int strStr(String haystack, String needle) {
        int m = haystack.length(),n = needle.length();
        for(int i = 0;i<=m-n;i++){
            boolean flag = true;
            for(int j =0;j<n;j++){
                if(haystack.charAt(i+j) != needle.charAt(j)){
                    flag = false;
                    break;
                }
            }
            if(flag){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int i = strStr("123abbb3", "3a");
        System.out.println(i);
    }
}

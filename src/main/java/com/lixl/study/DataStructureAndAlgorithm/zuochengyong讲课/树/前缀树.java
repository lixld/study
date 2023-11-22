package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树;

import java.util.HashMap;

/**
 * 也叫trie树
 * <p>
 * 组件该树的时间复杂度：O(n)
 * <p>
 * 作用1: 查询某个字符串 加入的次数
 * 作用2：所有加入的字符串中， 有几个是  以某个字符做前缀的？
 */
public class 前缀树 {
    public static class Node1 {
        int pass;
        int end;
        public Node1[] nexts;

        public Node1() {
            pass = 0;
            end = 0;
            /**
             *   0->a   第0个元素代表  a方向上的路
             *   1->b   第1个元素代表  b方向上的路
             *   ...
             *   25->z  第25个元素代表 z方向上的路
             *
             *   nexts[i]==null 表示 i方向的路不存在
             *
             *   nexts[i]！=null 表示 i方向的路存在
             */

            nexts = new Node1[26];
        }
    }

    public static class Node2 {
        int pass;
        int end;
        public HashMap<Integer, Node2> nexts;//字符种类很多的时候

    }

    public static class Trie1 {
        private Node1 root;

        public Trie1() {
            root = new Node1();
        }

        /**
         * 插入方法
         *
         * @param word
         */
        public void insert(String word) {
            if (word == null) {
                return;
            }

            char[] chars = word.toCharArray();
            Node1 node = root;
            node.pass++;

            for (int i = 0; i < chars.length; i++) {
                int index = chars[i] - 'a';//由字符，对应成走向哪条路
                Node1 next = node.nexts[index];
                if (next == null) {
                    next = new Node1();
                }
                node = next;
                node.pass++;
            }
            node.end++;
        }

        /**
         * 总体思路：
         * 1.先确定该字符串确实存在？
         * 2.让后沿途把pass--   最后把end--
         *
         * @param word
         */
        public void delete(String word) {
            if (search(word) == 0) {//shuo
                return;
            }
            char[] chars = word.toCharArray();
            Node1 node = root;
            node.pass--;
            int path = 0;
            for (int i = 0; i < chars.length; i++) {
                path = chars[i] - 'a';

                if (--node.nexts[path].pass == 0) {//如果下方节点--完以后 已经变成0了
                    node.nexts[path] = null;//则在父节点这里吧他置为null,其它元素让jvm找不到，就会被自动gc回收
                    return;
                }
                node = node.nexts[path];
            }
            node.end--;
        }

        /**
         * 字符串被加入的次数----其实就是求字符串最后一个字母（节点）end的值
         *
         * @param keyword
         * @return 加入的次数
         */
        public int search(String keyword) {
            if (keyword == null) {
                return 0;
            }
            char[] chars = keyword.toCharArray();
            Node1 node = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                Node1 next = node.nexts[index];
                if (next == null) {//说明不存在该字符串
                    return 0;//所以直接返回0
                }
                node = next.nexts[index];//否则就接着往下找
            }
            return node.end;
        }


        /**
         * 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
         */
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chars = pre.toCharArray();
            Node1 node = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                Node1 next = node.nexts[index];
                if (next == null) {//说明不存在该字符串
                    return 0;//所以直接返回0
                }
                node = next.nexts[index];//否则就接着往下找
            }
            return node.pass;
        }


    }
}

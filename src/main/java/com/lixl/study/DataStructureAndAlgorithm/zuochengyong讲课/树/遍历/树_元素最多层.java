package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树.遍历;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author lixinlin
 * @date 2023/2/10
 */
public class 树_元素最多层 {

    class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 借助数据结构：：：：：：队列
     * <p>
     * 考题2：
     * 延伸考题：获取元素最多的层
     * <p>
     * 本质：二叉树的横向遍历代码的解除上做的改动 下面口诀的①和②就是 二叉树横向遍历的代码 基础上加了一个map的取值赋值而已
     * <p>
     * 解法1：：借助map记录一个节点所在的层
     * 口诀：
     * ① 出队：弹出节点（当前节点为弹出节点）                 【map中获取到（弹出）节点 所在层数：A】
     * ② 入队：当前节点 有左-则左入队  + 当前节点 有右-则右入队 【同时设置节点的层数 map中put值】
     * <p>
     * ③ 层数A = 当前统计的层数B ->说明是在同一层   当前层的 宽度++
     * <p>
     * ④ 层数A!= 当前统计的层数B ->说明需要跃层了  a.当前统计层数+1  b.数量从1开始从新统计 c.计算最大值 max=（和当前层数量比较）
     * <p>
     * ⑤ 最后 全部出队后  max和  当前层数量 取最大
     *
     * @param head
     * @return
     */
    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        //key:节点    value: 在哪一层，
        HashMap<Node, Integer> levelMap = new HashMap<>();//把所有节点数据信息都放入map中
        levelMap.put(head, 1);

        Integer curLevel = 0;           //当前正在统计的层 是哪一层
        Integer curLevel_NodeNumber = 0;//当前正在统计的层 节点数量是多少？
        Integer max = 0;

        while (!queue.isEmpty()) {
            //出队
            Node pop = queue.poll();
            Integer popNodeLevel = levelMap.get(pop);//当前出队节点 --所在层数

            //入队  + 并记录位置
            Node left = pop.left;
            if (left != null) {
                queue.add(left);
                levelMap.put(left, popNodeLevel + 1);
            }
            Node right = pop.right;
            if (right != null) {
                queue.add(right);
                levelMap.put(right, popNodeLevel + 1);
            }

            //这里就是区别于横向遍历的代码
            if (popNodeLevel == curLevel) {//弹出的这一层 和  当前层  是在同一层
                curLevel_NodeNumber++;//只需要给当前层数量+1即可，不需要其他操作
            } else {//这里就是越层了。。进入下一层的操作   ①当前统计层+1  ②数量从1开始统计 ，③同时计算最大值max
                max = Math.max(max, curLevel_NodeNumber);
                curLevel++;
                curLevel_NodeNumber = 1;
            }
        }

        max = Math.max(max, curLevel_NodeNumber);
        return max;
    }

    /**
     * 考题2：
     * 延伸考题：获取元素最多的层
     * <p>
     * 解法2：：不借助map
     * <p>
     * 口诀：--准备队列（只有头结点）--死盯着这个队列，只要队列不为空就执行下面逻辑列表
     * ① 出队：弹出节点（当前节点为弹出节点）
     * ② 入队：当前节点[ 有左-则左入队  有右-则右入队] +【nextEnd随时跟着 先更新到下层的左、再跟新到下层的右】
     * ③ 当前层的节点数 ++
     * ④ 弹出节点 = 最右节点 a.结算max b.当前层的节点数 重新归0 c.nextEnd赋值给当前层
     * <p>
     * 最后max 就是值
     *
     * @param head
     * @return
     */
    public static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);


        Node curEnd = head;//当前层、最后节点是谁
        Node nextEnd = null;//如果有下一层，最右节点是谁

        int max = 0;
        int curLevelNodes = 0;//当前层节点数


        while (!queue.isEmpty()) {
            //出队
            Node pop = queue.poll();

            //入队--nextEnd随时跟着更新
            Node left = pop.left;
            if (left != null) {
                queue.add(left);
                nextEnd = left;
            }
            Node right = pop.right;
            if (right != null) {
                queue.add(right);//入队
                nextEnd = right;
            }

            curLevelNodes++;

            if (pop == curEnd) {//当前弹出节点，已经是最右节点--则结算，
                max = Math.max(max, curLevelNodes);
                curLevelNodes = 0;
                curEnd = nextEnd;
            }
        }


        return max;
    }

}

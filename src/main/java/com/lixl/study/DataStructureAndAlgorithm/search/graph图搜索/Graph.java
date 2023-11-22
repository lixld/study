package com.lixl.study.DataStructureAndAlgorithm.search.graph图搜索;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 图的表示可以有两种数据结构
 * 1.邻接矩阵
 * 2.邻接表
 * <p>
 * 这里用的是邻接表来表示图
 */
public class Graph {
    boolean found = false;

    public int v;
    public LinkedList<Integer>[] adj;

    // 无向图

    /**
     *
     * @param v  顶点的值 每个顶点都带这个一个链表（长度为v）
     */
    public Graph(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    /**
     * 给不同的节点 建立关联关系
     * @param s
     * @param t
     */
    public void addEdge(int s, int t) { // 无向图一条边存两次
        adj[s].add(t);
        adj[t].add(s);
    }


    /**
     * 深度优先
     * 地址：https://blog.csdn.net/weixin_44122303/article/details/122924246
     * <p>
     * 当你站在迷宫的某个岔口，想要找到出口，
     * 你随意选择一个岔口来走，走着走着发现走不通的时候，
     * 你就退回去上一个岔口，重新选择一条路继续走。
     * 不断递归重复此过程，直到所有顶点遍历完成，它的特点就是：不撞南墙不回头，先走完一条路，再换一条路继续走！
     * 树是图的一种特例（连通无环的图就是树）
     * 直到最终找到出口
     * <p>
     * <p>
     * 这种走法就是 深度优先搜索策略
     */

    public void dfs(int s, int t) {
        found = false;
        boolean[] visited = new boolean[v];
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }
        recurDfs(s, t, visited, prev);
        print(prev, s, t);
    }

    private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
        if (found == true) return;
        visited[w] = true;
        if (w == t) {
            found = true;
            return;
        }
        for (int i = 0; i < adj[w].size(); ++i) {
            int q = adj[w].get(i);
            if (!visited[q]) {
                prev[q] = w;
                recurDfs(q, t, visited, prev);
            }
        }
    }


    /**
     * 广度优先
     * @param s 起始顶点
     * @param t 终止顶点
     * 搜索一条从s到t的最短路径
     */
    public void bfs(int s, int t) {
        if (s == t) return;
        boolean[] visited = new boolean[v];//记录已经被访问的顶点，用来避免顶点被重复访问。如果顶点 q 被访问，那相应的 visited[q]会被设置为 true。
        visited[s] = true;
        Queue<Integer> queue = new LinkedList<>();//存储已经①被访问+②但相连的顶点还没有被访问的顶点
//        因为广度优先搜索是逐层访问的，也就是说，我们只有把第 k 层的顶点都访问完成之后，才能访问第 k+1 层的顶点。当我们访问到第 k 层的顶点的时候，我们需要把第 k 层的顶点记录下来，稍后才能通过第 k 层的顶点来找第 k+1 层的顶点。所以，我们用这个队列来实现记录的功能。


        queue.add(s);
        int[] prev = new int[v];//用来记录搜索路径

        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }
        while (queue.size() != 0) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); ++i) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
    }

    private void print(int[] prev, int s, int t) { // 递归打印s->t的路径
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }
}

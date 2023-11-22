package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树;

import java.util.LinkedList;
import java.util.Queue;

public class 树_序列化_反序列化 {

    static class Node {
        private Integer value;
        private Node left;
        private Node right;

        public Node(Integer value) {
            this.value = value;
        }
    }


    //region 基于上下遍历（前序遍历） 的序列化 和 反序列化

    /**
     * 按照前-中-后序 遍历
     * 序列化
     *
     * @param head
     * @return
     */

    public Queue<String> preSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        pres(head, ans);
        return ans;
    }


    //（先序遍历）序列化（前序遍历--把对象放入队列中）
    //中序和后序就是上面代码中 位置变一下【递归序改一下即可】
    private void pres(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    /**
     * 反序列化（还原）为树
     */
    public Node preUnSerial(Queue<String> queue) {
        String value = queue.poll();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.left = preUnSerial(queue);
        head.right = preUnSerial(queue);
        return head;
    }
    //endregion


    /**
     * 按照----层遍历  依然是以按层遍历为基础代码，在此基础上做改动
     * <p>
     * 把树结构，填充完整后，放入队列中
     *
     * @param head
     * @return
     */
    //按层遍历 --实现序列化 借助一个队列即可
    public Queue<String> levelSerial(Node head) {
        //序列化 结果队列
        Queue<String> answers = new LinkedList<>();
        if (head == null) {
            answers.add(null);
        } else {
            answers.add(String.valueOf(head.value));

            Queue<Node> queue = new LinkedList<>();//
            queue.add(head);
            while (!queue.isEmpty()) {
                head = queue.poll();//不停弹出
                Node left = head.left;
                Node right = head.right;
                if (left != null) {
                    answers.add(String.valueOf(left.value));
                    queue.add(left);
                } else {
                    answers.add(null);
                }
                if (right != null) {
                    answers.add(String.valueOf(right.value));
                    queue.add(right);
                } else {
                    answers.add(null);
                }
            }
        }
        return answers;
    }
    // 反序列化（基于按层遍历）--通过队列构建树
    public Node levelUnserial(Queue<String> levelList) {

        if (levelList == null || levelList.size() == 0) {
            return null;
        }
        String head_val = levelList.poll();
        Node head = generateNode(head_val);

        Queue<Node> queue = new LinkedList<>();

        if (head != null) {
            queue.add(head);
        }
        Node pop;
        while (!queue.isEmpty()) {
            pop = queue.poll();
            String left_val = levelList.poll();
            String right_val = levelList.poll();
            Node popLeft = generateNode(left_val);//可能是空，所以下面要做判断
            Node popRight = generateNode(right_val);//可能是空，所以下面要做判断
            pop.left = popLeft;
            pop.right = popRight;

            if (popLeft != null) {
                queue.add(pop.left);
            }
            if (popRight != null) {
                queue.add(pop.right);
            }

        }
        return head;
    }
    private Node generateNode(String poll) {
        if (poll == null) {
            return null;
        }
        Node node = new Node(Integer.valueOf(poll));
        return node;

    }
}

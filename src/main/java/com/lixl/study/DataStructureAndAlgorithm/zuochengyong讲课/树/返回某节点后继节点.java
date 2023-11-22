package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树;

/**
 * @author lixinlin
 * @date 2023/2/13
 * <p>
 * 给你二叉树中某个节点、返回该节点的后继节点
 * <p>
 * 后继节点：中序遍历中某个节点的下一个
 *
 * 前趋节点，和这个方向正好反着来就是了
 * 解法①：
 * 解法②：
 * 节点x 有左树？左树最右节点
 * 节点x 有右数？
 */
public class 返回某节点后继节点 {
    public class Node {
        Object value;
        Node left;
        Node right;
        Node parent;//指向它的父节点
    }

    /**
     * 解法1：
     * ①根据parent一直找到，树的头部head
     * ②遍历这个二叉树，找到给定元素的下一个即可
     * 缺点：用中（前后）序遍历 时间复杂度O(N),其实时间复杂度可以变低为O(K) k为给定节点和它后继节点的距离
     */

    public Object getNextNode1(Node x) {

        Node head = null;
        while (x.parent != null) {
            head = x.parent;
        }
        midbianli(head, x);

        return null;
    }

    public Node midbianli(Node node, Node x) {

        midbianli(node.left, x);

        if (node.parent == x) {
            return node;
        }

        midbianli(node.right, x);
        return null;//没找到返回null
    }

    /**
     * 解法2：
     * 节点X  有右树？->最左节点
     * 节点x  没有右数？->一直往上找(指针替换)，直到找到它是某个父亲的左孩子，该父亲就是它的后继节点
     */

    public Object getNextNode(Node x) {

        if (x == null) {
            return x;
        }
        Node right = x.right;
        if (right != null) {
            return getLeftMost(right);
        } else {//无右树
            Node parent = x.parent;
            //找到第一个右节点

            while (parent != null && x.equals(parent.right)) {//当前节点是其父亲节点的右孩子
                x = parent;//指针一直向上位移
                parent = x.parent;
            }
            return parent;
        }
    }

    /**
     * 找到右树的最左节点
     *
     * @param right
     * @return
     */
    private Object getLeftMost(Node right) {
        Node left = right.left;
        while (left != null) {
            left = left.left;
        }
        return left;
    }


}

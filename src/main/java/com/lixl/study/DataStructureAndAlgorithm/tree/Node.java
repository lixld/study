package com.lixl.study.DataStructureAndAlgorithm.tree;

/**
 * @author : lixl
 * @date : 2020-10-15 09:46:31
 **/
public class Node {
    private Object data;
    private Node left;
    private Node right;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}

package com.lixl.study.DataStructureAndAlgorithm.tree;

/**
 * @author : lixl
 * @date : 2020-10-15 09:45:31
 **/
public class Tree {

    public static  void preOrder(Node root){
        if (root == null) {
            return;
        }
        System.out.println(root);
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }


    public static  void inOrder(Node root){
        if (root == null) {
            return;
        }
        inOrder(root.getLeft());
        System.out.println(root);
        inOrder(root.getRight());
    }

    public static  void postOrder(Node root){
        if (root == null) {
            return;
        }
        postOrder(root.getLeft());
        postOrder(root.getRight());
        System.out.println(root);
    }

    public static void main(String[] args) {
        Node level1 = new Node();
        level1.setData("aa");

        Node level2_left = new Node();
        level2_left.setData("bb");

        Node level2_right = new Node();
        level2_right.setData("cc");

        level1.setLeft(level2_left);
        level1.setRight(level2_right);

        postOrder(level1);
    }
}

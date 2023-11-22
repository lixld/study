package com.lixl.study.DataStructureAndAlgorithm.tree;

import java.util.LinkedList;

public class BinarySearchTree {
    private Node tree;//已经准备好的二叉查找树

    //快速查找
    public Node find(int data) {
        Node p = tree;
        while (p != null) {
            if (data < p.data) p = p.left;
            else if (data > p.data) p = p.right;
            else return p;
        }
        return null;
    }

    //快速插入
    public void insert(int data) {
        if (tree == null) {
            tree = new Node(data);
            return;
        }
        Node p = tree;
        while (p != null) {
            if (data > p.data) {
                if (p.right == null) {
                    p.right = new Node(data);
                    return;
                }
                p = p.right;
            } else if (data < p.data) {
                if (p.left == null) {
                    p.left = new Node(data);
                    return;
                }
                p = p.left;
            } else {
                p = null;
            }
        }
    }

    //查找树中某个节点下的最小值
    public static Node min(Node node) {
        if (node.left == null && node.right == null) {
            return node;
        }
        Node leftMin = null;
        while (node.left != null) {
            node = node.left;
            leftMin = node;
        }
        return leftMin;
    }

    //查找树中某个节点下的最大值
    public static Node max(Node node) {
        if (node.left == null && node.right == null) {
            return node;
        }
        Node max = null;
        while (node.right != null) {
            node = node.right;
            max = node;
        }
        return max;
    }

    public static void main(String[] args) {
        Node node33 = new Node(33);
        Node node16 = new Node(16);
        Node node50 = new Node(50);
        Node node13 = new Node(13);
        Node node18 = new Node(18);
        Node node34 = new Node(34);
        Node node58 = new Node(58);
        Node node15 = new Node(15);
        Node node17 = new Node(17);
        Node node25 = new Node(25);
        Node node51 = new Node(51);
        Node node66 = new Node(66);
        Node node19 = new Node(19);
        Node node27 = new Node(27);
        Node node55 = new Node(55);
        node33.left = node16;
        node33.right = node50;
        node16.left = node13;
        node16.right = node18;
        node50.left = node34;
        node50.right = node58;
        node13.right = node15;
        node18.left = node17;
        node18.right = node25;
        node58.left = node51;
        node58.right = node66;
        node25.left = node19;
        node25.right = node27;
        node51.right = node55;

//        Node min = min(node33);
//        System.out.println(min);
//
//        Node max = max(node33);
//        System.out.println(max);
//
//        Node.levelOrder(node33);

        Node delete = delete(node33, 33);

        Node.levelOrder(delete);

    }

    //辅助数据结构
    public class ParentAndSon {
        Node parent;
        Node son;

        public ParentAndSon(Node parent, Node son) {
            this.parent = parent;
            this.son = son;
        }

    }

    public ParentAndSon getDateParent(Node tree, int data) {
        Node p = tree;
        Node pp = null;
        while (p != null && p.data != data) {
            pp = p;
            if (data > p.data) {
                p = p.right;
                pp.right = p;
            } else {
                p = p.left;
                pp.left = p;
            }
            if (p == null) {
                return null;//没找到
            }
        }
        ParentAndSon parentAndSon = new ParentAndSon(pp, p);

        return parentAndSon;
    }

    //删除查找树中某个节点

    /**
     * 第一种情况是，如果要删除的节点没有子节点，我们只需要直接将父节点中，指向要删除节点的指针置为 null。比如图中的删除节点 55。
     * 第二种情况是，如果要删除的节点只有一个子节点（只有左子节点或者右子节点），我们只需要更新父节点中，指向要删除节点的指针，让它指向要删除节点的子节点就可以了。比如图中的删除节点 13。
     * 第三种情况是，如果要删除的节点有两个子节点，这就比较复杂了。我们需要找到这个节点的右子树中的最小节点，把它替换到要删除的节点上。
     * 然后再删除掉这个最小节点，因为最小节点肯定没有左子节点（如果有左子结点，那就不是最小节点了），所以，我们可以应用上面两条规则来删除这个最小节点。比如图中的删除节点 18。
     * @param tree
     * @param data
     * @return
     */
    public static Node delete(Node tree, int data) {
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        ParentAndSon dateParent = binarySearchTree.getDateParent(tree, data);
        if (dateParent == null) {
            return tree;//没找到,直接结束
        }
        Node p = dateParent.son;
        Node pp = dateParent.parent;

        boolean leftNull = p.left == null;
        boolean rightNull = p.right == null;
        if (leftNull && rightNull) {
            pp.left = null;
            pp.right = null;
        } else if (!leftNull && !rightNull) {//左右都有
            Node min = min(p.right);
            ParentAndSon dateParent1 = binarySearchTree.getDateParent(tree, min.data);
            System.out.println(min);
            int data1 = min.data;
            p.data = data1;
            Node parent = dateParent1.parent;
            Node left = parent.left;
            Node right = parent.right;
            if (left.data == min.data) {
                parent.left = null;
            }
            if (right.data == min.data) {
                parent.right = null;
            }
            //
        } else {//只要一个为空
            if (leftNull) {//
                Node right = p.right;
                p.data = right.data;
                p.right = null;
            }
            if (rightNull) {
                Node left = p.left;
                p.data = left.data;
                p.left = null;
            }
        }
        return tree;
    }


    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }

        //分层打印树结构
        public static void levelOrder(Node root) {
            if (root == null) {
                return;
            }
            LinkedList<Node> queue = new LinkedList<Node>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node currentNode = queue.poll();
                System.out.print(currentNode.data + " ");
                if (currentNode.left != null) {
                    queue.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.add(currentNode.right);
                }
            }
        }


    }
}

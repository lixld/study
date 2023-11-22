package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.树;

/**
 * @author lixinlin
 * @date 2023/2/13
 */
public class 打印树形状 {
    class Node {
        private String value;
        private Node left;
        private Node right;
    }

    public static void printTree(Node head) {
        System.out.println("开始答应树结构");
        printInOrder(head, 0, "H", 17);
        System.out.println("打印完毕");
    }

    private static void printInOrder(Node head, int height, String to, int length) {

        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", length);//打印右边,就是下箭头


        generatePrintString(head, height, to, length);

        printInOrder(head.left, height + 1, "^", length);//打印左边，就是上箭头

    }

    private static void generatePrintString(Node node, int height, String to, int length) {
        String val = to + node.value + to;
        int length_middle = val.length();
        int length_left = (length - length_middle) / 2;
        int length_right = length - length_middle - length_left;

        val = getSpace(length_left) + val + getSpace(length_right);
        System.out.println(getSpace(height * length) + val);

    }

    private static String getSpace(int length) {

        String space = " ";
        StringBuffer stringBuffer = new StringBuffer("");
        for (int i = 0; i < length; i++) {
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }
}

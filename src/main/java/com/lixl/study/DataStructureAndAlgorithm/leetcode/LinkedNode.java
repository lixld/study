package com.lixl.study.DataStructureAndAlgorithm.leetcode;

/**
 * 链表数据结构
 */
public class LinkedNode<T> {
    public LinkedNode<T> next;
    public T value;

    public LinkedNode() {
    }

    public LinkedNode(T value) {
        this.value = value;
    }

    public static LinkedNode initLink(int[] values) {
        LinkedNode linkedNode = LinkedNodeArrayUtils.arry2LinkedNode(values);
        return linkedNode;
    }

    public static void soutLinkedNode(LinkedNode<Integer> linkedNode) {
        StringBuffer stringBuffer = new StringBuffer();
        while (linkedNode != null) {
            int value1 = linkedNode.value;
            stringBuffer.append(value1 + "-->");
            linkedNode = linkedNode.next;
        }
        System.out.println(stringBuffer.toString());
    }


}
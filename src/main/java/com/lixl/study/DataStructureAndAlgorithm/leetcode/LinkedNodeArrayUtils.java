package com.lixl.study.DataStructureAndAlgorithm.leetcode;

/**
 * 数组《-》链表 装换工具类
 */
public class LinkedNodeArrayUtils {

    /**
     * 一、是否需要哨兵？
     * 同时具备以下两种情况，需要安排一个哨兵
     * ①不是在原先链表操作指针+
     * ②最后还需要返回链表--->>
     * 比如链表反转--头插法，就是在原先链表上操作指针，不满足条件，所以不需要哨兵
     * 比如链表反转--三指针法，在原先链表上操作指针，不满足条件，所以不需要哨兵
     * 比如链表反转--入栈法，就是不是在原先链表上操作指针，并且还要返回链表，满足条件，所以需要哨兵。
     * <p>
     * 二、是否需要内存中临时节点？
     * 如果只是中途需要借助一下内存临时保存，则只需要一个临时节点即可temp
     *
     * @param arr
     * @return
     */
    public static LinkedNode arry2LinkedNode(int[] arr) {
        LinkedNode sentinalNode = new LinkedNode();//它就站在门口站岗不动，等需要的时候（返回时）找它
        LinkedNode cur = sentinalNode;//准备操作的临时节点
        for (int i = 0; i < arr.length; i++) {
            int i1 = arr[i];
            LinkedNode linkedNode = new LinkedNode(i1);
            cur.next = linkedNode;
            cur = cur.next;//=的含义是：左--指向->右 指针要右移
        }
        return sentinalNode.next;
    }

    public static int[] linkedNode2Arry(LinkedNode<Integer> linkedNode) {
        LinkedNode temp = linkedNode;
        int arrSize = 0;
        while (temp != null) {
            arrSize++;
            temp = temp.next;
        }
        int[] ints = new int[arrSize];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = linkedNode.value;
            linkedNode = linkedNode.next;
        }
        return ints;
    }

    public static void main(String[] args) {
        int[] ints = {1, 2, 3, 4};
        LinkedNode linkedNode = arry2LinkedNode(ints);
        LinkedNode.soutLinkedNode(linkedNode);

        int[] ints1 = linkedNode2Arry(linkedNode);
        for (int i = 0; i < ints1.length; i++) {
            System.out.println(ints1[i]);
        }
    }





}

package com.lixl.study.DataStructureAndAlgorithm.leetcode;

//对有序的链表合并为一个新有序链表
public class LinkedNodeMerge {
    public  static LinkedNode mergeLinked(LinkedNode list1, LinkedNode list2) {

        LinkedNode newHead = new LinkedNode();

        LinkedNode cur = newHead;
//        while (list1 != null && list2 != null) {
//
//            if (list1.value < list2.value) {
//                cur.next = list1;
//                list1 = list1.next;
//            } else {
//                cur.next = list2;
//                list2 = list2.next;
//            }
//        }
        cur.next = list1 == null ? list2 : list1;


        return newHead;
    }

    public static void main(String[] args) {
        int[] l1 = {1,2,4};
        int[] l2 = {1,3,4} ;
//
//        mergeLinked(l1,l2);


    }
}

package com.lixl.study.DataStructureAndAlgorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

public class LinkedNodeHasCircle {

    //①hash表
    public boolean hasCircle(LinkedNode head) {
        Set<LinkedNode> set = new HashSet();
        while (head != null) {
            if (!set.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    //③双指针 快慢指针---详细解题思路看processon图解
    public LinkedNode hasCircleIndex(LinkedNode head) {
        LinkedNode index1;
        LinkedNode index2;
        LinkedNode fast = head;
        LinkedNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;//如果大于等于3个则，可能一直错过，永远不相遇，但是如果只有两步，则一定会相遇（现对于慢指针，快指针是以一个节点(这样就肯定能相遇了，因为已经是最小单位了)的速度追赶它）
            slow = slow.next;
            if (fast == slow) {//到这里（也就是相遇了），也就是肯定是有环了！因为没有环是不可能相遇的!!!
                //下面的代码就是在找环点在哪里，根据图中分析，其实就是再来两个指针（一个指向head,一个指向slow（fast也可以，因为此时他们是一样的位置）），同时向着环点进发，相遇时就是环点了！
                index1 = head;
                index2 = slow;
                while (index1 != index2) {
                    index1 = index1.next;
                    index2 = index2.next;
                }
                return index2;//index1都是一样的
            }
        }
        return null;
    }
}

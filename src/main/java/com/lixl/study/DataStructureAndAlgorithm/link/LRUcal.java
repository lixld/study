package com.lixl.study.DataStructureAndAlgorithm.link;

/**
 * @author : lixl
 * @date : 2020-11-16 21:28:24
 **/
public class LRUcal {

    class Node {
        String data;
        Node pre;
        Node next;
    }

    private Node header;
    private Node tail;
    private int LRUSize;

    public LRUcal(Node header, Node tail, int LRUSize) {
        this.header = header;
        this.tail = tail;
        this.LRUSize = LRUSize;
    }

    private static int getLinkElementNumber(Node header) {
        int result = 0;
        while (header.next != null) {

            result++;

            header = header.next;
        }
        return result;
    }

    public void LRU(Node newNode) {

        int linkElementNumber = getLinkElementNumber(header);

        while (header.next != null) {
            String data = header.data;
            String newNodeData = newNode.data;
            if (data.equals(newNodeData)) {//找到
                header.pre = header.next;
                tail.next = newNode;
                break;
            } else {
                if (linkElementNumber == LRUSize) {
                    header = header.next;
                    tail.next = newNode;
                }else {
                    tail.next = newNode;
                }
            }
            header = header.next;
        }
    }
}

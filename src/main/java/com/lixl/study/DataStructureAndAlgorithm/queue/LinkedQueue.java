package com.lixl.study.DataStructureAndAlgorithm.queue;

/**
 * @author : lixl
 * @date : 2020-10-28 10:07:19
 **/
public class LinkedQueue implements IqueueMethod{
    private Node header = null;
    private Node tail = null;

    @Override
    public String dequeue() {
        if(header==null){
            return null;
        }
        Node next = header.next;
        header = next;
        return header.getData();
    }

    @Override
    public boolean enqueue(String item) {
        Node node = new Node(item,null);
        if (tail == null) {
            header= node;
            tail = node;
            return true;
        }

        tail.next = node;
        tail = node;
        return true;
    }

    class Node{
        private String data;
        private Node next;

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
        }

        public String getData() {
            return data;
        }
    }

}

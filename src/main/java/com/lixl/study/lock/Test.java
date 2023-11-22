package com.lixl.study.lock;

public class Test {

    class Node {
        int index;
        Node next;
        Object data;
    }

    public Node getIndexk(Node node,Integer m) {

        int nodelength = getNodelength(node);
        int index = nodelength - m;
        if (nodelength >= index){
            while (node.next!=null){
                node.index = m;
                return node;
            }
        }else {
            //不存在
            return null;
        }
        return null;
    }

    public Integer getNodelength(Node node) {
        if (node == null) {
            return 0;
        }
        int i = 1;
        while (node.next != null) {
            i++;
            node = node.next;
        }
        return i;
    }


}

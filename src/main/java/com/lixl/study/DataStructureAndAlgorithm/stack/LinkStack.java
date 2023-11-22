package com.lixl.study.DataStructureAndAlgorithm.stack;

/**
 * @author : lixl
 * @date : 2020-10-20 17:36:26
 **/
public class LinkStack implements IStackMethod {
    private java.util.LinkedList items; //链表
    private int count;  //栈中元素个数
    private int n;     //栈大小

    @Override
    public boolean push(String item) {
        if (count == n) return false;
        items.addLast(item);
        count++;
        return true;
    }

    @Override
    public String pop() {
        items.pop();
        return null;
    }

}

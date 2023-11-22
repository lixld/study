package com.lixl.study.DataStructureAndAlgorithm.stack;

/**
 * @author : lixl
 * @date : 2020-10-20 17:00:55
 **/
public class ArrayStack implements IStackMethod {
    private String[] items; //数组
    private int count;      //栈中元素个数
    private int n ;         //栈大小

    public ArrayStack(int n) {
        this.items = new String[n];
        this.count = 0;
        this.n = n;
    }
    public boolean push(String item){
        if (count==n) return false;
        items[count] = item;
        ++count;
        return true;
    }
    public String pop(){
        if (count==0) return null;
        String item = items[count-1];
        --count;
        return item;
    }
}

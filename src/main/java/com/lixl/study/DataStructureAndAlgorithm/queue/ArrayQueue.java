package com.lixl.study.DataStructureAndAlgorithm.queue;

// 用数组实现的队列
public class ArrayQueue implements IqueueMethod{
    // 数组：items，数组大小：n
    private String[] items;
    private int n = 0;
    // head表示队头下标，tail表示队尾下标
    private int head = 0;
    private int tail = 0;

    // 申请一个大小为capacity的数组
    public ArrayQueue(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    // 入队
//    public boolean enqueue(String item) {
//        // 如果tail == n 表示队列已经满了
//        if (tail == n) return false;
//        items[tail] = item;
//        ++tail;
//        return true;
//    }

    // 出队
    public String dequeue() {
        // 如果head == tail 表示队列为空
        if (head == tail) return null;
        // 为了让其他语言的同学看的更加明确，把--操作放到单独一行来写了
        String ret = items[head];
        ++head;
        return ret;
    }

    // 入队操作，将item放入队尾
    public boolean enqueue(String item) {
        // tail == n表示队列末尾没有空间了
        if (tail == n) {
            // tail ==n && head==0，表示整个队列都占满了
            if (head == 0) return false;
            // 数据搬移
            for (int i = head; i < tail; ++i) {
                items[i-head] = items[i];
            }
            // 搬移完之后重新更新head和tail
            tail -= head;
            head = 0;
        }

        items[tail] = item;
        ++tail;
        return true;
    }

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(5);
        String dequeue = arrayQueue.dequeue();
        System.out.println(dequeue);
        arrayQueue.enqueue("111");
        arrayQueue.enqueue("222");
        arrayQueue.enqueue("333");
        arrayQueue.enqueue("444");
        String dequeue1 = arrayQueue.dequeue();
        System.out.println(dequeue1);
        String dequeue2 = arrayQueue.dequeue();
        System.out.println(dequeue2);
        System.out.println(arrayQueue);
    }
}

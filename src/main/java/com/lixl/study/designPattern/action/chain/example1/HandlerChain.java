package com.lixl.study.designPattern.action.chain.example1;

import java.util.ArrayList;
import java.util.List;

public class HandlerChain {
    /*
    链表实现
    private Handler head = null;
    private Handler tail = null;

    public void addHandler(Handler handler) {
        handler.setSuccessor(null);
        if (head == null) {
            head = handler;
            tail = handler;
            return;
        }
        tail.setSuccessor(handler);
        tail = handler;
    }

    public void handle() {
        if (head != null) {
            head.handle();
        }
    }*/

    //TODO 数组实现
    private List<Handler> allHanders = new ArrayList<>();

    public void addHandler(Handler handler) {
        allHanders.add(handler);
    }

    public void handle() {
        for (Handler allHander : allHanders) {
            allHander.handle();
        }
    }

}

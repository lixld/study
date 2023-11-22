package com.lixl.study.designPattern.action.chain.example1;

public class HandlerA extends Handler {


    @Override
    protected boolean doHandle() {
        System.out.println("handlerA.....");
        boolean handled = false;
        return handled;
    }
}

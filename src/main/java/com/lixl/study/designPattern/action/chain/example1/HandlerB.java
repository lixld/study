package com.lixl.study.designPattern.action.chain.example1;

public class HandlerB extends Handler {

    @Override
    protected boolean doHandle() {

        System.out.println("handlerB.....");
        boolean handled = false;
        return handled;
    }
}

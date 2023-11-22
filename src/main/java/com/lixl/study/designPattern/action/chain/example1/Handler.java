package com.lixl.study.designPattern.action.chain.example1;

public abstract class Handler {
    protected Handler successor = null;

//    public void setSuccessor(Handler successor) {
//        this.successor = successor;
//    }

    public final void handle() {
        doHandle();
//        if (successor != null ) {
//            successor.handle();
//        }
    }
    protected abstract boolean doHandle();
}

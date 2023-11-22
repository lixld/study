package com.lixl.study.designPattern.action.templete.demo2_callback;

public class BClass {

    public void process(ICallback callback) {
        //...
        callback.methodToCallback();

        System.out.println("BClass中的其它逻辑");
    }
}

package com.lixl.study.designPattern.struct.Adapter.对象适配器组合实现;

import com.lixl.study.designPattern.struct.Adapter.ITarget;
import com.lixl.study.designPattern.struct.Adapter.Adaptee;

public class Adaptor implements ITarget {
    private Adaptee adaptee;

    public Adaptor(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    public void f1() {
        adaptee.fa();//委托给Adaptee
    }

    public void f2() {
        //...重新实现f2()...
    }

    public void fc() {
        adaptee.fc();
    }
}

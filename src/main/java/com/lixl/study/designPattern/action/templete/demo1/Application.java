package com.lixl.study.designPattern.action.templete.demo1;

public class Application {
    public static void main(String[] args) {
        AbstractClass demo = new ConcreateClass1();
        demo.templateMethod();

        AbstractClass demo2 = new ConcreateClass2();
        demo2.templateMethod();





    }
}

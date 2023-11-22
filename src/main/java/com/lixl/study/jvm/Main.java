package com.lixl.study.jvm;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("hello word!");
        System.in.read();
        test();
    }

    private static void test() {
        System.out.println("-----------test---------");
    }
}

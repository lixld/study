package com.lixl.study.jvm;

/**
 * 类加载测试类
 */
public class HelloService {

    public static String value = getValue();
    static {
        System.out.println("********static code*******");
    }
    public static String getValue(){
        System.out.println("##########static method");
        return "lixl static method";
    }

    private static void test() {

        System.out.println("-----------test---------"+value);
    }
}

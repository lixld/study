package com.lixl.study.thread.threadpool.mythreadPool;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Test {

    /**
     * 方法有静态方法(static)和非静态方法，
     * 但函数式接口关注的仅是入参、出参类型和个数而已：
     *
     * @param str
     * @return
     */
    public void voidMethod(){
        System.out.println("111");
    }
    public Long noStaticFoo(String str) {
        return Long.parseLong(str) * 30;
    }

    public static Long staticFoo(String str) {
        return Long.parseLong(str) * 40;
    }



    public static void main(String[] args) {
        Test test = new Test();
        /*Function<String, Long> staticMath = Test::staticFoo;
        Long apply1 = staticMath.apply("23");
        System.out.println(apply1);
        // 静态方法，类::
        Function<String, Long> staticFoo = Test::staticFoo;
        Long apply = staticFoo.apply("100");
        System.out.println(apply);

        // 非静态方法，实例对象::

        Function<String, Long> noStaticFoo = voidMethod::noStaticFoo;
        Long apply2 = noStaticFoo.apply("100");
        System.out.println(apply2);


        *//* 这种可以理解成两个入参、除了原来方法的入参外，还要指明实例对象（因为这是一个实例方法）*//*
        BiFunction<Test, String, Long> noStaticFooTest = Test::noStaticFoo;
        Long apply3 = noStaticFooTest.apply(voidMethod, "100");
        System.out.println(apply3);
        */

        AsyncExecutor asyncExecutor = new AsyncExecutor();
        Future async = asyncExecutor.async(test::voidMethod);
        try {
            Object o = async.get();
            System.out.println("------"+o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        Future<Long> ssss = asyncExecutor.async(Test::staticFoo, "ssss");
//        try {
//            Long aLong = ssss.get();
//            System.out.println(aLong);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }
}

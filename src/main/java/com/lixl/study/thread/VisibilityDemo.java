//package com.lixl.study.thread;
//
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//
//public class VisibilityDemo {
//    private boolean flag = true;
////    private volatile boolean flag = true;
//
//
//    volatile int i = 0;
//
////    volatile AtomicInteger i = new AtomicInteger();
////    public void  addAtomic(){
////        int i = this.i.incrementAndGet();
////
////    }
//
//
//    /**
//     * 这样拿不到，需要用反射才行
//     */
////    private static Unsafe unsafe = Unsafe.getUnsafe();
//    private static Unsafe unsafe;
//    private static long valueOffset = 0;//属性偏移量，用于JVM去定位属性在内存中的地址
//
//    /**
//     * 需要用反射才行获取到Unsafe对象
//     */
//    static {
//        try {
//            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
//            theUnsafe.setAccessible(true);
//            unsafe = (Unsafe) theUnsafe.get(null);
//
//            //CAS 硬件原语 ---java语言无法直接修改内存. 曲线通过对象及属性的定为方式
//            /**
//             * 先查找到对象（当前类）在内存中的位置
//             * 再查找到属性相对于对象（当前类）的偏移量offset
//             */
//            valueOffset = unsafe.objectFieldOffset(VisibilityDemo.class.getField("i"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void add() {
////        System.out.println(unsafe);
//
//        //i++ //三次操作
//        /**
//         * 参数解释如下
//         * 对象  对象的属性偏移量  当前值  目标值
//         */
////        boolean b = unsafe.compareAndSwapInt(this, valueOffset, i, value);//CAS低层API，只有两个参数
//        int current;
//        int value;
//        do {
//            current = i;//读取当前值
//            value = current + 1;//计算
//
//        } while (unsafe.compareAndSwapInt(this, valueOffset, i, value));//因为该操作可能失败，所以需要循环重试多次
//
//
//    }
//
//
//    public static void main(String[] args) throws InterruptedException {
//      VisibilityDemo visibilityDemo = new VisibilityDemo();
// /*
//        Thread thread1 = new Thread(() -> {
//            visibilityDemo.flag = false;
//        });
//
//        Thread thread2 = new Thread(() -> {
//
//            if (thread1.isAlive()) {//只有线程1中断--此时意味着线程1已经执行结束了
//                //TODO 去读取flag---线程1的修改，对线程2可见
//
//                System.out.println(visibilityDemo.flag);
//            }
//        });
//
//
//        System.out.println("代码开始了");
//        new Thread(() -> {
//            int i = 0;
//            while (visibilityDemo.flag) {
//                i++;
//            }
//            System.out.println(i);
//        }).start();
//        Thread.sleep(10000l);
//        visibilityDemo.flag = false;
//        System.out.println("被设置为false了");*/
//
//        for (int i = 0; i < 2; i++) {
//            new Thread(()->{
//                visibilityDemo.add();
//            });
//        }
//    }
//}

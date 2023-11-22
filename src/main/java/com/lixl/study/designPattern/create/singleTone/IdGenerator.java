package com.lixl.study.designPattern.create.singleTone;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 1. 单例对 OOP 特性的支持不友好
 * 单例违背了基于接口而非实现的设计原则，也就违背了广义上理解的 OOP 的抽象特性。
 * 如果未来某一天，我们希望针对不同的业务采用不同的 ID 生成算法。
 * 比如，订单 ID 和用户 ID 采用不同的 ID 生成器来生成。为了应对这个需求变化，我们需要修改所有用到 IdGenerator 类的地方，
 * 这样代码的改动就会比较大。
 * <p>
 * 对继承、多态特性的支持也不友好
 * <p>
 * 2. 单例会隐藏类之间的依赖关系
 * <p>
 * 3. 单例对代码的扩展性不友好
 * <p>
 * 4. 单例对代码的可测试性不友好
 * <p>
 * 5. 单例不支持有参数的构造函数；比如我们创建一个连接池的单例对象，需要指定连接池大小
 * <p>
 * 思路1：创建完实例之后，再调用 静态的init() 函数传递参数
 * 使用这个单例类的时候，要先调用 init() 方法，然后才能调用 getInstance() 方法
 * Singleton.init(10, 50); // 先init，再使用Singleton
 * singleton = Singleton.getInstance();//如果缺失第一步则，直接报错
 * <p>
 * 思路2：将参数放到 getInstance() 方法中
 * Singleton singleton = Singleton.getInstance(10, 50);
 * <p>
 * 思路3：将参数放到另外一个全局变量中
 */


public class IdGenerator {
    private AtomicLong id = new AtomicLong(0);
    /**
     * 饿汉式，直接就new了，
     * 不支持延迟加载，如果实力占用资源多，或者初始化耗时长(如：需要加载各种配置文件)
     * 是一种浪费资源的行为
     * 最好是应用到的时候再去初始化，我个人并不认同这个观点
     */
    private static final IdGenerator instance = new IdGenerator();

    private IdGenerator() {
    }

    public static IdGenerator getInstance() {
        return instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }


    static class Config {
        public static final int PARAM_A = 123;
        public static final int PARAM_B = 245;
    }

    static class Singleton {
        private static Singleton instance = null;
        private final int paramA;
        private final int paramB;

        private Singleton() {
            this.paramA = Config.PARAM_A;
            this.paramB = Config.PARAM_B;
        }

        public synchronized static Singleton getInstance() {
            if (instance == null) {
                instance = new Singleton();
            }
            return instance;
        }
    }

    /**
     * 基于单例的这些问题，有没有好的替代方案？----静态方法
     * 1.包装类的全局唯一？ 单例||静态方法
     */


    // 静态方法实现方式
   static class IdGenerator_Static {
        private static AtomicLong id = new AtomicLong(0);
        public static long getId() {
            return id.incrementAndGet();
        }
    }


}

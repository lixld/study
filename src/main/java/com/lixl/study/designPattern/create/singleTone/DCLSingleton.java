package com.lixl.study.designPattern.create.singleTone;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Double Check Lock
 */
public class DCLSingleton {

    private AtomicLong id = new AtomicLong(0);

    private volatile static DCLSingleton instance;//高版本的jdk,已经在内部解决了这个问题（new对象和初始化操作设计为原子操作）。可以不用加volatile禁止指令重排序

    private DCLSingleton() {
    }

    public static DCLSingleton getInstance() {
        //没有直接进来就加锁方法执行，而是先检查
        if (instance == null) {
            synchronized (DCLSingleton.class) {//这里是类锁
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }


    /**
     *  单例扩展
     *
     * 我们这里说的单例，其实是进程级别的单例，就是一个进程内，一个对象只有有一份实例，
     * 问题1：如何实现一个线程间的单例--java实现ThreadLocal{自己实现用currentHashMap存线程id，和对象，用putIfAbsent去取--其实也就是ThreadLocal的实现原理}
     * 问题2：如何实现一个集群环境下的的单例--分布式单例--把单例对象系列化并存储到外部共享存储区（如文件）
     * 进程在使用这个单例对象的时候，从外部共享区中将它读取到内存，并反序列化成对象。然后再使用。使用完再存储回去
     * 为了保证任何时候，在进程之间只能有一份对象存在，一个进程在获取对象只有，要对对象加锁。避免其他进程再次获取。使用完显式地将对象在内存中删除，并释放锁
     */


    static class IdGenerator {
        private AtomicLong id = new AtomicLong(0);
        private static IdGenerator instance;
        private static SharedObjectStorage storage = new SharedObjectStorage();//FileSharedObjectStorage(/*入参省略，比如文件地址*/);
        private static DistributedLock lock = new DistributedLock();

        private IdGenerator() {
        }

        public synchronized static IdGenerator getInstance() {
            if (instance == null) {
                lock.lock();
                instance = storage.load(IdGenerator.class);
            }
            return instance;
        }

        public synchronized void freeInstance() {
            storage.save(this, IdGenerator.class);
            instance = null; //释放对象
            lock.unlock();
        }

        public long getId() {
            return id.incrementAndGet();
        }
    }

    /*// IdGenerator使用举例
    IdGenerator idGeneator = IdGenerator.getInstance();
    long id = idGenerator.getId();
    IdGenerator.freeInstance();*/

}

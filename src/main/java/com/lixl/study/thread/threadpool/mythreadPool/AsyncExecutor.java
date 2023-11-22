package com.lixl.study.thread.threadpool.mythreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.*;

public class AsyncExecutor {
    // 线程池，建议恰当配置（拒绝策略建议CallerRunsPolicy）和使用框架注入
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 无入参，无返回值的异步执行方法 , void noStaticFoo()
     *
     * @param method 要执行的方法，如 user::noStaticFoo;
     * @return Future对象，用以判断是否执行结束
     */
    public Future async(Runnable method) {
        return executorService.submit(method);
    }

    /**
     * 有单个入参，无返回值的异步执行方法，如 void noStaticFoo(Long id)
     *
     * @param method 要执行的方法，如, user::noStaticFoo
     * @param paramValue  方法执行的入参，如id
     * @param <ParamType>    入参类型，如Long
     * @return Future对象，用以判断是否执行结束
     */
    public <ParamType> Future async(Consumer<ParamType> method, ParamType paramValue) {
        return executorService.submit(() -> method.accept(paramValue));
    }

    /**
     * 有两个参数但是无返回值的异步执行方法, 如void noStaticFoo(Long id,Entity entity)
     *
     * @param method 要执行的方法，如 , user::noStaticFoo
     * @param param1 第一个入参值，如id
     * @param param2 二个入参值，如entity
     * @param <P1>   第一个入参类型
     * @param <P2>   第二个入参类型
     * @return Future对象，用以判断是否执行结束
     */
    public <P1, P2> Future async(BiConsumer<P1, P2> method, P1 param1, P2 param2) {
        return executorService.submit(() -> method.accept(param1, param2));
    }

    /**
     * 无参数有返回值的异步执行方法 , Entity noStaticFoo()
     *
     * @param method 要执行的方法，如 , user::noStaticFoo
     * @param <R>    返回值类型,如 Entity
     * @return Future对象，用以判断是否执行结束、获取返回结果
     */
    public <R> Future<R> async(Supplier<R> method) {
        return executorService.submit(method::get);
    }

    /**
     * 单个入参，有返回值的异步执行方法 , Entity noStaticFoo(Long id)
     *
     * @param method 要执行的方法，如 , user::noStaticFoo
     * @param param  入参值，如 id
     * @param <P>    入参类型，如Long
     * @param <R>    返回值类型,如 Entity
     * @return Future对象，用以判断是否执行结束、获取返回结果
     */
    public <P, R> Future<R> async(Function<P, R> method, P param) {
        return executorService.submit(() -> method.apply(param));
    }

    /**
     * 两个入参，有返回值的异步执行方法 , Entity noStaticFoo(Long id)
     *
     * @param method 要执行的方法，如 , user::noStaticFoo
     * @param param1 第一个入参值，如id
     * @param param2 二个入参值，如entity
     * @param <P1>   第一个入参类型
     * @param <P2>   第二个入参类型
     * @param <R>    返回值类型,如 Entity
     * @return Future对象，用以判断是否执行结束、获取返回结果
     */
    public <P1, P2, R> Future<R> async(BiFunction<P1, P2, R> method, P1 param1, P2 param2) {
        return executorService.submit(() -> method.apply(param1, param2));
    }
}

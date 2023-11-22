package com.lixl.study.designPattern.create.factory.DI.MyDI;

public class RateLimiter {
    private RedisCounter redisCounter;


    public RateLimiter(RedisCounter redisCounter) {
        this.redisCounter = redisCounter;
    }

    public void test() {
        System.out.println("Hello World!");
    } //...


}

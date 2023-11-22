package com.lixl.study.idempotence.ratelimit.google_guava_rateLimiter;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentMap;

public class Test6 {

    //key-value (service,Qps) ，接口服务的限制速率
    private static final ConcurrentMap<String, Double> resourceMap = Maps.newConcurrentMap();
    //userkey-service,limiter ,限制用户对接口的访问速率
    private static final ConcurrentMap<String, RateLimiter> userResourceLimiterMap = Maps.newConcurrentMap();

    static {
        //init ，初始化方法A的Qps为50
        resourceMap.putIfAbsent("methodA", 10.0);
    }

    public static void updateResourceQps(String resource, double qps) {
        resourceMap.put(resource, qps);
    }

    public static void removeResource(String resource) {
        resourceMap.remove(resource);
    }

    public static int enter(String resource, String userKey) {
        long t1 = System.currentTimeMillis();
        Double qps = resourceMap.get(resource);

        //不限流
        if (qps == null || qps.doubleValue() == 0.0) {
            return 0;
        }

        String keySer = resource + userKey;
        RateLimiter rateLimiter = userResourceLimiterMap.get(keySer);
        //if null , new limit
        if (rateLimiter == null) {
            rateLimiter = RateLimiter.create(qps);

            RateLimiter putByOtherThread = userResourceLimiterMap.putIfAbsent(keySer, rateLimiter);
            if (putByOtherThread != null) {
                rateLimiter = putByOtherThread;
            }
            rateLimiter.setRate(qps);
        }

        //非阻塞
        if (!rateLimiter.tryAcquire()) {
            //限速中，提示用户
            System.out.println("use :" + (System.currentTimeMillis() - t1) + "ms ; "
                    + resource + " visited too frequently by key:" + userKey);
            return 99;
        } else {
            //正常访问
            System.out.println("use :" + (System.currentTimeMillis() - t1) + "ms ; ");
            return 0;
        }

    }
}

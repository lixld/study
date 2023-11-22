package com.lixl.study.redis;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class semaphore {
    //信号量限流
    Semaphore findUserByIdsemaphoren = new Semaphore(50);//一次性发50个手牌，根据你的业务系统机器而定

    public Object findUserById(String userId){
        Object cacheValue = null;
        if (cacheValue != null) {
            System.out.println("从缓存中取数据");
            return  cacheValue;
        }
        //大量请求怼数据库 导致雪崩 -----2000+并发
//        findUserByIdsemaphoren.acquire();//获取一个许可，如果没有就等待

        try {
            //等待---用户等待不及---等待时长
            boolean result = findUserByIdsemaphoren.tryAcquire(3000, TimeUnit.MILLISECONDS);
            if (!result){
                //TODO 降级策略
                //默认值
                //返回制动的错误码
                //指定错误


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            String sql  = "select * from xxx where user_id = "+userId;
            //数据库 --连接池 --限流  为什么不使用数据库连接池来来解决

            System.out.println("这里执行业务逻辑"+userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            findUserByIdsemaphoren.release();//释放许可
        }

        return null;
    }


}

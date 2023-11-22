package com.lixl.study.beautyController.aop;

import com.lixl.study.beautyController.enums.CacheTypeEnum;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheAnnocation {

    /**
     * 缓存的键
     */
    String cacheKey() default "";

    boolean cacheKeyAppendArgs() default true;

    /**
     * 缓存过期时间
     */
    int expireSecond() default 300;

    /**
     * 缓存实现类型
     */
    CacheTypeEnum type() default CacheTypeEnum.REDIS;
}


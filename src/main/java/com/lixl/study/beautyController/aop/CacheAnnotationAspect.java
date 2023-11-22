package com.lixl.study.beautyController.aop;


import com.lixl.study.beautyController.enums.CacheTypeEnum;
import com.lixl.study.beautyController.utils.JsonUtils;
import com.lixl.study.beautyController.utils.LocalCache;
import com.lixl.study.beautyController.utils.MethodUtils;
import com.lixl.study.beautyController.utils.UserUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class CacheAnnotationAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(com.lixl.study.beautyController.aop.CacheAnnocation)")
    public void define(){}

    @Around("define()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Class controllerClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Method method = MethodUtils.getMethod(controllerClass, methodName);
        CacheAnnocation cacheAnnotation = method.getAnnotation(CacheAnnocation.class);
        CacheTypeEnum cacheType = cacheAnnotation.type();
        String cacheKey = cacheAnnotation.cacheKey();
        int expireSecond = cacheAnnotation.expireSecond();
        boolean cacheKeyAppendArgs = cacheAnnotation.cacheKeyAppendArgs();
        String userId = UserUtils.getUserId();

        StringBuilder keysb = new StringBuilder(userId).append("-");
        keysb.append(ObjectUtils.isEmpty(cacheKey) ? (controllerClass + "-" + methodName + "-") : cacheKey);
        if (cacheKeyAppendArgs && !ObjectUtils.isEmpty(args)) {
            for (Object arg : args) {
                keysb.append(JsonUtils.toJsonStr(arg)).append("-");
            }
        }
        String key = keysb.toString();

        if (cacheType == CacheTypeEnum.REDIS) {
            String val = stringRedisTemplate.opsForValue().get(key);
            if (!ObjectUtils.isEmpty(val)) {
                Type type = method.getGenericReturnType();
                Object o = JsonUtils.toObject(val, type);
                return o;
            } else {
                Object o = joinPoint.proceed();
                val = JsonUtils.toJsonStr(o);
                stringRedisTemplate.opsForValue().set(key, val);
                stringRedisTemplate.expire(key, expireSecond, TimeUnit.SECONDS);
                return o;
            }
        } else if (cacheType == CacheTypeEnum.LOCAL) {
            LocalCache.Builder builder = new LocalCache.Builder().type(cacheType.name() + expireSecond).expire(expireSecond, TimeUnit.SECONDS);
            LocalCache localCache = builder.build();
            Object val = localCache.get(key);
            if (ObjectUtils.isEmpty(val)) {
                val = joinPoint.proceed();
                localCache.put(key, val);
            }
            return val;
        } else {
            throw new IllegalArgumentException("can not resolve cacheType " + cacheType);
        }
    }
}

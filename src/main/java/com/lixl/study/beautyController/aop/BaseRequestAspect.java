package com.lixl.study.beautyController.aop;

import com.lixl.study.beautyController.dto.BaseRequestDTO;
import com.lixl.study.beautyController.enums.CycleEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BaseRequestAspect {


    @Pointcut("execution(public * com.lixl.study.*.*(..))")
    public void define(){}

    @Around("define()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object[] argArr = joinPoint.getArgs();
            for (Object arg : argArr) {
                if (arg instanceof BaseRequestDTO) {
                    BaseRequestDTO req = (BaseRequestDTO) arg;
                    CycleEnum cycleEnum = req.getCycleEnum();
                    Integer year = req.getYear();
                    Integer cycleNum = req.getCycleNum();
                    if (cycleEnum == null) {
                        throw new IllegalArgumentException("cycle is required!");
                    }
                    if (year == null) {
                        throw new IllegalArgumentException("year is required!");
                    }
                    if (cycleNum == null) {
                        throw new IllegalArgumentException("cycleNum is required!");
                    }
                    req.fill();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Object res = joinPoint.proceed();
        return res;
    }
}

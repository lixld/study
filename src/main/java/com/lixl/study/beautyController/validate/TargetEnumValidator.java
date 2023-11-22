package com.lixl.study.beautyController.validate;


import com.lixl.study.beautyController.aop.TargetEnumValidatorAnnocation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class TargetEnumValidator implements ConstraintValidator<TargetEnumValidatorAnnocation, Object> {

    private TargetEnumValidatorAnnocation annotation;

    @Override
    public void initialize(TargetEnumValidatorAnnocation constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Object[] objects = annotation.clazz().getEnumConstants();
        try {
            Method method = annotation.clazz().getMethod(annotation.method());
            for (Object o : objects) {
                if (value.equals(method.invoke(o))) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
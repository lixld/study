package com.lixl.study.beautyController.aop;


import com.lixl.study.beautyController.validate.TargetEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(TargetEnumValidatorAnnocation.List.class)
@Constraint(validatedBy = {TargetEnumValidator.class})
public @interface TargetEnumValidatorAnnocation {

    String message() default "{*.validation.constraint.Enum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * the enum's class-type
     *
     * @return Class
     */
    Class<?> clazz();

    /**
     * the method's name ,which used to validate the enum's value
     *
     * @return method's name
     */
    String method() default "ordinal";

    /**
     * Defines several {@link TargetEnumValidatorAnnocation} annotations on the same element.
     *
     * @see TargetEnumValidatorAnnocation
     */
    @Documented
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @interface List {
        TargetEnumValidatorAnnocation[] value();
    }
}
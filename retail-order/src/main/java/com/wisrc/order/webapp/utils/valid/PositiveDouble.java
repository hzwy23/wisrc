package com.wisrc.order.webapp.utils.valid;


import com.wisrc.order.webapp.utils.valid.validation.PositiveDoubleConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositiveDoubleConstraintValidator.class)
public @interface PositiveDouble {
    //自定义注解必须实现这三个属性
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
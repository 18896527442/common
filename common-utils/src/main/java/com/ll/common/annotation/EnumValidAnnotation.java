package com.ll.common.annotation;

import com.ll.common.utils.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Validate 校验枚举类型，校验的值为枚举类型
 * 例如枚举类<code>CustomerIdentityEnu</code>，前端传的参数为VISITOR
 * 大小写不区分
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValidator.class})
@Documented
public @interface EnumValidAnnotation {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?>[] target() default {};
}

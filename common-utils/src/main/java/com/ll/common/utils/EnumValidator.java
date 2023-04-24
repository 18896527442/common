package com.ll.common.utils;

import com.smart.common.annotation.EnumValidAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumValidator implements ConstraintValidator<EnumValidAnnotation, String> {
    Class<?>[] cls;

    @Override
    public void initialize(EnumValidAnnotation enumValidAnnotation) {
        cls = enumValidAnnotation.target();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (cls.length > 0) {
            for (Class<?> cl : cls) {
                try {
                    if (cl.isEnum()) {
                        // 枚举类验证
                        Object[] objs = cl.getEnumConstants();
                        Method method = cl.getMethod("toString");
                        for (Object obj : objs) {
                            Object code = method.invoke(obj, null);
                            if (value.equalsIgnoreCase(code.toString())) {
                                return true;
                            }
                        }
                    }
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return true;
        }
        return false;
    }
}
package com.ll.common.spring.web.valid.handler;

import cn.hutool.core.collection.CollUtil;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;

@Component
public class MinLengthValidHandler extends AbstractValidHandler {
    public MinLengthValidHandler() {
        super(Length.class);
    }

    @Override
    public String getType() {
        return "minLength";
    }

    @Override
    public boolean support(Annotation annotation, Class fieldType) {
        boolean support = super.support(annotation, fieldType);
        if (support){
            Length length = (Length) annotation;
            if (length.max() == Integer.MAX_VALUE){
                return true;
            }
        }
        return support;
    }

    @Override
    public List<Object> handler(Annotation annotation) {
        Length length = (Length) annotation;
        return CollUtil.newArrayList(length.min(),getAnnotationMessage(annotation));
    }
}

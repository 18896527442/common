package com.ll.common.spring.web.valid.handler;

import cn.hutool.core.collection.CollUtil;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;


@Component
public class MaxLengthValidHandler extends AbstractValidHandler {
    public MaxLengthValidHandler() {
        super(Length.class);
    }

    @Override
    public String getType() {
        return "maxLength";
    }

    @Override
    public boolean support(Annotation annotation, Class fieldType) {
        boolean support = super.support(annotation, fieldType);
        if (support){
            Length length = (Length) annotation;
            if (length.min() == 0){
                return true;
            }
        }
        return support;
    }

    @Override
    public List<Object> handler(Annotation annotation) {
        Length length = (Length) annotation;
        return CollUtil.newArrayList(length.max(),getAnnotationMessage(annotation));
    }
}

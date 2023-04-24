package com.ll.common.spring.web.valid.handler;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import java.lang.annotation.Annotation;
import java.util.List;

@Component
public class MinValidHandler extends AbstractValidHandler {
    public MinValidHandler() {
        super(Min.class);
    }

    @Override
    public String getType() {
        return "min";
    }


    @Override
    public List<Object> handler(Annotation annotation) {
        Min min = (Min) annotation;
        return CollUtil.newArrayList(min.value(),getAnnotationMessage(annotation));
    }
}

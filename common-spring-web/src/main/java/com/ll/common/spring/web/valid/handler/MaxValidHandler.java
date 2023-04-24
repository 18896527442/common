package com.ll.common.spring.web.valid.handler;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import java.lang.annotation.Annotation;
import java.util.List;

@Component
public class MaxValidHandler extends AbstractValidHandler {
    public MaxValidHandler() {
        super(Max.class);
    }

    @Override
    public String getType() {
        return "max";
    }


    @Override
    public List<Object> handler(Annotation annotation) {
        Max max = (Max) annotation;
        return CollUtil.newArrayList(max.value(),getAnnotationMessage(annotation));
    }
}

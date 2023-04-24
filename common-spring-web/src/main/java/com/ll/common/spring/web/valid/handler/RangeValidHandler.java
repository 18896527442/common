package com.ll.common.spring.web.valid.handler;

import cn.hutool.core.collection.CollUtil;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;


@Component
public class RangeValidHandler extends AbstractValidHandler {
    public RangeValidHandler() {
        super(Range.class);
    }

    @Override
    public String getType() {
        return "range";
    }


    @Override
    public List<Object> handler(Annotation annotation) {
        Range range = (Range) annotation;

        return CollUtil.newArrayList(CollUtil.newArrayList(range.min(),range.max()),
                getAnnotationMessage(annotation));
    }
}

package com.ll.common.spring.web.valid.handler;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.List;

@Component
public class NumberValidHandler extends AbstractValidHandler {
    public NumberValidHandler() {
        super(Long.class,Integer.class, BigDecimal.class);
    }

    @Override
    public String getType() {
        return "number";
    }


    @Override
    public List<Object> handler(Annotation annotation) {
        return CollUtil.newArrayList(true,"数字格式不正确");
    }
}

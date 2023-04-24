package com.ll.common.spring.web.valid.handler;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.util.List;


@Component
public class RegexValidHandler extends AbstractValidHandler {
    public RegexValidHandler() {
        super(Pattern.class);
    }

    @Override
    public String getType() {
        return "regex";
    }


    @Override
    public List<Object> handler(Annotation annotation) {
        Pattern pattern = (Pattern) annotation;
        return CollUtil.newArrayList(pattern.regexp(),getAnnotationMessage(annotation));
    }
}

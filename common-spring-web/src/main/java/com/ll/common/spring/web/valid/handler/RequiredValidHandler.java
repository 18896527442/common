package com.ll.common.spring.web.valid.handler;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.List;


@Component
public class RequiredValidHandler extends AbstractValidHandler {
    public RequiredValidHandler() {
        super(NotNull.class, NotEmpty.class, NotBlank.class,
                org.hibernate.validator.constraints.NotBlank.class,
                org.hibernate.validator.constraints.NotEmpty.class);
    }

    @Override
    public String getType() {
        return "required";
    }


    @Override
    public List<Object> handler(Annotation annotation) {
        return CollUtil.newArrayList(true,getAnnotationMessage(annotation));
    }
}

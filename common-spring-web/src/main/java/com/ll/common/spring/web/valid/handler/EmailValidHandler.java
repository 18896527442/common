package com.ll.common.spring.web.valid.handler;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.lang.annotation.Annotation;
import java.util.List;

@Component
public class EmailValidHandler extends AbstractValidHandler {
    public EmailValidHandler() {
        super(Email.class, org.hibernate.validator.constraints.Email.class);
    }

    @Override
    public String getType() {
        return "email";
    }


    @Override
    public List<Object> handler(Annotation annotation) {
        return CollUtil.newArrayList(true,getAnnotationMessage(annotation));
    }
}

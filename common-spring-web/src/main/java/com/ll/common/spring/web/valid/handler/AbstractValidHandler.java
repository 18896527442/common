package com.ll.common.spring.web.valid.handler;

import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractValidHandler<T> {
    protected Class<?>[] supportList;
    private static ConcurrentHashMap<String, String> msgMap;
    static {
        msgMap = new ConcurrentHashMap<>();
        msgMap.put("{javax.validation.constraints.NotNull.message}","该字段必填");
        msgMap.put("{javax.validation.constraints.NotEmpty.message}","该字段必填");
        msgMap.put("{javax.validation.constraints.NotBlank.message}","该字段必填");
        msgMap.put("{org.hibernate.validator.constraints.NotBlank.message}","该字段必填");
        msgMap.put("{org.hibernate.validator.constraints.NotEmpty.message}","该字段必填");
        msgMap.put("{org.hibernate.validator.constraints.Length.message}","字段长度不正确");
    }




    public AbstractValidHandler(Class... supportList) {
        this.supportList = supportList;
    }

    public abstract String getType();
    public boolean support(Annotation annotation,Class fieldType){
        if (annotation == null){
            return false;
        }else{
            for (Class aClass : supportList) {
                if (aClass == annotation.annotationType()){
                    return true;
                }
            }
        }
        if (fieldType != null){
            for (Class<?> aClass : supportList) {
                if (aClass == fieldType){
                    return true;
                }
            }

        }

        return false;
    }
    public abstract  <T> T handler(Annotation annotation);


    protected String getAnnotationMessage(Annotation annotation){
        Method message = ReflectionUtils.findMethod(annotation.getClass(), "message");
        try {
            String msg = (String) message.invoke(annotation);
            return msgMap.getOrDefault(msg,msg);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }

    }
}

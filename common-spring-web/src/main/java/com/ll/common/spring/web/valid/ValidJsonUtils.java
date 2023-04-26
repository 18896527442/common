package com.ll.common.spring.web.valid;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.ll.common.spring.web.valid.handler.AbstractValidHandler;
import com.ll.common.utils.FieldUtils;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ValidJsonUtils {
    @Autowired
    List<AbstractValidHandler> validHandlers;
    private ConcurrentHashMap<Class<?>, HashMap<String, HashMap<String, Object>>> cacheMap;

    @PostConstruct
    protected void init(){
        cacheMap = new ConcurrentHashMap<>(1024);
    }


    public HashMap<String, HashMap<String, Object>> parseValid(Class<?> cls){
        if (cls == null){
            return new HashMap<>(1);
        }
        HashMap<String, HashMap<String, Object>> hashMap = cacheMap.get(cls);
        if (hashMap != null){
            return hashMap;
        }
        hashMap = new HashMap<>(20);
        List<Field> fields = FieldUtils.getAllField(cls);

        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            HashMap<String, Object> fieldMap = new HashMap<>(annotations.length);
            for (Annotation annotation : annotations) {
                Optional.ofNullable(getHandler(annotation,field.getType()))
                        .ifPresent(validHandler -> {
                            Object rs = validHandler.handler(annotation);
                            fieldMap.put(validHandler.getType(),rs);
                        });
            }
            Optional.ofNullable(getHandler(null,field.getType())).ifPresent(validHandler -> {
                Object rs = validHandler.handler(null);
                fieldMap.put(validHandler.getType(),rs);
            });

            hashMap.put(field.getName(),fieldMap);
        }

        cacheMap.put(cls,hashMap);
        return hashMap;
    }

    public AbstractValidHandler getHandler(Annotation annotation, Class fieldType){
        for (AbstractValidHandler validHandler : validHandlers) {
            if (validHandler.support(annotation,fieldType)){
                return validHandler;
            }
        }
        return null;
    }



    public static void main1(String[] args) {
        ValidJsonUtils jsonUtils = new ValidJsonUtils();

        HashMap<String, HashMap<String, Object>> map = jsonUtils.parseValid(Test.class);
        System.out.println(JSON.toJSONString(map));

    }
    @Data
    public static class Test extends InTest{
        @NotNull(message = "xxxx")
        @Min(10)
        @Max(20)
        @Range(min=1,max = 100)
        private Integer id;
    }

    @Data
    public static class InTest{
        @NotBlank
        @Length(min = 10,max = 100)
        @Email
        @Pattern(regexp = "xxx")
        private String user;
        @NotEmpty
        private DateTime dateTime;
    }
}

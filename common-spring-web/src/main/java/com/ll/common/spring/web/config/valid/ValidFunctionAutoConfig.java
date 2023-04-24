package com.ll.common.spring.web.config.valid;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import com.ll.common.spring.web.valid.ValidJsonUtils;
import com.ll.common.core.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
@Import({ValidProperties.class, ValidJsonUtils.class})
public class ValidFunctionAutoConfig {

    @Autowired
    ValidProperties validProperties;
    @Autowired
    ValidJsonUtils validJsonUtils;
    @Bean
    @ConditionalOnProperty(name = "ll.valid.enable",havingValue = "true")
    public RouterFunction<ServerResponse> init(RequestMappingHandlerMapping handlerMapping){

        log.info("[校验路由]-自动配置开始");
        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
        Iterator<Map.Entry<RequestMappingInfo, HandlerMethod>> iter = map.entrySet().iterator();
        Map.Entry<RequestMappingInfo, HandlerMethod> entry;
        RouterFunctions.Builder route = RouterFunctions.route();



        while (iter.hasNext()) {
            entry = iter.next();
            if (entry.getValue().getBeanType().getName().startsWith(validProperties.getBasePackage())) {
                addAndParse(entry, route);
            }
        }
        route.GET("/hello-valid",(serverRequest)->
                ServerResponse.accepted().body("success"));
        log.info("[校验路由]-自动配置完成");
        return route.build();
    }

    protected void addAndParse(Map.Entry<RequestMappingInfo, HandlerMethod> entry, RouterFunctions.Builder route) {
        String path = new ArrayList<>(entry.getKey().getPatternsCondition().getPatterns()).get(0);

        try {
            //去除 path 占位符: /xxx/{xx}
            String newPath = ReUtil.delAll("/\\{\\w+\\}", path);
            boolean match = ReUtil.findAll("\\{\\w+\\}", newPath,0).size()>0;
            if (match){
                log.warn("[校验路由]-该规则不支持自动添加校验路由:{}",path);
                return;
            }

            Method method = entry.getValue().getMethod();
            Class<?> paramType = null;
            ArrayList<Class<?>> paramTypeList = CollUtil.newArrayList(method.getParameterTypes());
            if (method.getParameterCount() > 1){
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                for (int i = 0; i < parameterAnnotations.length; i++) {
                    Annotation[] annotation = parameterAnnotations[i];
                    Class<?> type = method.getParameterTypes()[i];

                    if (checkHasAnnotation(annotation, RequestBody.class)){
                        //判断是否被requestBody注解
                        paramType = method.getParameterTypes()[i];
                        break;
                    }
                    if (checkHasAnnotation(annotation, PathVariable.class)){
                        paramTypeList.remove(type);
                    }
                    if (checkIsBaseType(type)){
                        paramTypeList.remove(type);
                    }
                }
                if (paramType == null){
                    if (paramTypeList.size() > 0){
                        paramType = paramTypeList.get(0);
                    }else{
                        log.warn("[校验路由]-添加失败,未找到合适的参数:{}",path);
                        return;
                    }
                }
            }else if (method.getParameterCount() >0){
                paramType = method.getParameterTypes()[0];
            }
            if (paramType == null){
                log.info("[校验路由]-不自动添加,未识别到参数");
                return;
            }
            Class<?> finalParamType = paramType;
            String fullPath = newPath + "-valid";
            log.info("[校验路由]-添加:{} -> {}",fullPath,finalParamType);
            route.GET(fullPath, serverRequest ->
                    ServerResponse.accepted()
                            .body(new Result<>(validJsonUtils.parseValid(finalParamType))));
        }catch (Exception ex){
            log.error("[校验路由]-添加失败",ex);
        }

    }

    private List<Class<?>> baseTypeList = CollUtil.newArrayList(
            String.class,Integer.class,Long.class,Byte.class,Short.class,Enum.class);

    private boolean checkIsBaseType(Class<?> parameterType) {
        for (Class<?> type : baseTypeList) {
            if (type == parameterType){
                return true;
            }
        }
        return false;
    }

    /**
     * 检查 是否包含指定注解
     * @param annotations
     * @param checkCls
     * @return
     */
    protected boolean checkHasAnnotation(Annotation[] annotations, Class<? extends Annotation> checkCls){
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == checkCls){
                return true;
            }
        }
        return false;
    }
}

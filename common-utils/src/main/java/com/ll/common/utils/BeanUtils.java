package com.ll.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public abstract class BeanUtils extends org.springframework.beans.BeanUtils {
    /**
     * 重载copy属性的方法，不会把null值的属性复制过去。
     * @param source
     * @param target
     * @throws BeansException
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    String writeMethodName = "";
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            writeMethodName = writeMethod.getName();

                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            String sourceType = value.getClass().getName();
                            String targetType = "";
                            if (writeMethod.getParameterTypes().length > 0) {
                                targetType = writeMethod.getParameterTypes()[0].getName();
                            }
                            // 检查set方法参数，兼容其他类型转为字符串
                            if (!sourceType.equals(targetType) && "java.lang.String".equals(targetType)) {
                                writeMethod.invoke(target, value.toString());
                            } else {
                                writeMethod.invoke(target, value);
                            }
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target, method: "+writeMethodName, ex);
                    }
                }
            }
        }
    }


    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }

        return;

    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {

        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!"class".equals(key)) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }

    public static Map transStringToMap(String mapString){
        Map map = new HashMap();
        StringTokenizer items;
        for(StringTokenizer entrys = new StringTokenizer(mapString, "^");entrys.hasMoreTokens();
            map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "'");
        return map;
    }

    public static Map<String,String> mapStringToMap(String str){
        Map<String,String> map = new HashMap<String, String>();
        if(StringUtils.isBlank(str) || str.length()<2){
            return map;
        }

        str=str.substring(1, str.length()-1);
        String[] strs=str.split(",");

        for (String string : strs) {
            String[] tempStr=string.split("=");
            if(tempStr.length<2)continue;
            String key=tempStr[0];
            String value=tempStr[1];
            map.put(key, value);
        }
        return map;
    }

    public static <T> List<T> copyList(List<?> sourceList, Class<T> clazz) {
        if (sourceList != null && sourceList.size() > 0) {
            List<T> list = new ArrayList<>();
            for (Object object : sourceList) {
                T t = null;
                try {
                    t = clazz.newInstance();
                    BeanUtils.copyProperties(object, t);
                    list.add(t);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }
        return null;
    }
}

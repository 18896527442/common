package com.ll.common.utils;

import cn.hutool.core.collection.CollUtil;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xuejike
 * @date 2020/11/26
 */
public class FieldUtils {

    public static List<Field> getAllField(Class<?> cls){
        LinkedList<Field> list = new LinkedList<>();
        while (cls != Object.class){
            Field[] fields = cls.getDeclaredFields();
            CollUtil.addAll(list,fields);
            cls = cls.getSuperclass();
        }
        return list;
    }

}

package com.ll.common.utils;


import java.util.Collection;
import java.util.Map;

public class EmptyUtil {
    private EmptyUtil() {
    }

    public static Boolean isEmpty(Collection<?> collection) {
        return isNull(collection) || collection.size() < 1;
    }

    public static Boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.size() < 1;
    }

    public static Boolean isEmpty(Object[] array) {
        return isNull(array) || array.length < 1;
    }

    public static Boolean isEmpty(String text) {
        return isNull(text) || text.trim().length() < 1;
    }

    public static Boolean isEmpty(Object object) {
        if (object instanceof Collection) {
            return isEmpty((Collection)object);
        } else if (object instanceof Map) {
            return isEmpty((Map)object);
        } else if (object instanceof Object[]) {
            return isEmpty((Object[])((Object[])object));
        } else {
            return object instanceof String ? isEmpty((String)object) : isNull(object);
        }
    }

    public static Boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static Boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static Boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static Boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    public static Boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    private static Boolean isNull(Object object) {
        return object == null;
    }
}


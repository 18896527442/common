package com.ll.common.utils.lambda;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class LambdaTool {
    static {
        propertyParseList = ServiceLoader.load(PropertyParse.class);
        simplePropertyParse = new SimplePropertyParse();
    }

    private static ServiceLoader<PropertyParse> propertyParseList;
    private static SimplePropertyParse simplePropertyParse;

    protected static String methodToProperty(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else if (name.startsWith("get") || name.startsWith("set")) {
            name = name.substring(3);
        } else {
            throw new RuntimeException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
        }

        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    protected static Map<Class, WeakReference<String>> cache = new ConcurrentHashMap<>();

    public static<T> String getName(FieldFunction<T,?> fieldFun){
        Class<? extends FieldFunction> cls = fieldFun.getClass();
        return Optional.ofNullable(cache.get(cls))
                .map(WeakReference::get)
                .orElseGet(()->{
                    SerializedLambda lambda = SerializedLambda.resolve(fieldFun);
                    for (PropertyParse parse : propertyParseList) {
                        String name = parse.parse(lambda);
                        if (name != null){
                            WeakReference<String> wr = new WeakReference<>(name);
                            cache.put(cls,wr);
                            return name;
                        }
                    }
                    return simplePropertyParse.parse(lambda);
                });
    }
}

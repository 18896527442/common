package com.ll.common.utils.lambda;

import java.util.Locale;

public class SimplePropertyParse implements PropertyParse{
    @Override
    public String parse(SerializedLambda serializedLambda) {
        String methodName = serializedLambda.getImplMethodName();
        String property = methodToProperty(methodName);
        return property;
    }

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
}

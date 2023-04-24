package com.ll.common.spring.web.cache;

@FunctionalInterface
public interface CacheCallback<T> {

    T callback();

}

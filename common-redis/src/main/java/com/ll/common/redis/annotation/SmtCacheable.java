package com.ll.common.redis.annotation;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

@Caching(cacheable = {
        @Cacheable(value = "smt_cache", key = "#p0")
})
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SmtCacheable {

}

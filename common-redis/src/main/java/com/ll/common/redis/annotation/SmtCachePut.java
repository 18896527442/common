package com.ll.common.redis.annotation;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

@Caching(put = {
        @CachePut(value = "smt_cache", key = "#p0.id")
})
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SmtCachePut {

}

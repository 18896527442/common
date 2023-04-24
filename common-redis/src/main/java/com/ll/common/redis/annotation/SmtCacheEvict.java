package com.ll.common.redis.annotation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

@Caching(evict = {
        @CacheEvict(value = "smt_cache", key = "#p0")
})
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SmtCacheEvict {

}

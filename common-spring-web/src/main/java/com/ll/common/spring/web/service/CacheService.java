package com.ll.common.spring.web.service;

import com.ll.common.spring.web.cache.CacheCallback;

public interface CacheService {

    <T> T cache(String key, CacheCallback<T> callback);

    <T> T cache(String key, CacheCallback<T> callback,Long expireTime);
}

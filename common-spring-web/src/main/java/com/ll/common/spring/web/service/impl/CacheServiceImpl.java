package com.ll.common.spring.web.service.impl;

import com.ll.common.spring.web.service.CacheService;
import com.smart.common.redis.RedisService;
import com.ll.common.spring.web.cache.CacheCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisService redisService;

    @Override
    public <T> T cache(String key, CacheCallback<T> callback) {
        T value = (T) redisService.get(key);
        if (value == null) {
            value = callback.callback();
            redisService.set(key, value);
            redisService.setExpireTime(key, 300L);
        }
        return value;
    }

    @Override
    public <T> T cache(String key, CacheCallback<T> callback, Long expireTime) {
        T value = (T) redisService.get(key);
        if (value == null) {
            value = callback.callback();
            redisService.set(key, value);
            redisService.setExpireTime(key, expireTime);
        }
        return value;
    }

}

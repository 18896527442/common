package com.ll.common.utils;

import com.smart.common.redis.RedisService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class CacheUtils {
    public static final TimeUnit DEFAULT_CACHE_TIME_UNIT = TimeUnit.SECONDS;
    public static final long DEFAULT_CACHE_TIME = 60 * 60;
    public static final long SIMPLE_CACHE_TIME = 5;
    public static final TimeUnit SIMPLE_CACHE_TIME_UNIT = TimeUnit.MINUTES;

    @Autowired
    @Lazy
    private RedissonClient redissonClient;


//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisService redisService;

    private void notEmpty(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("缓存异常");
        }
    }

    public <T> void cacheList(List<T> list, String key) {
        notEmpty(key);
        redisService.set(key,list,DEFAULT_CACHE_TIME);
        //stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(list), DEFAULT_CACHE_TIME, DEFAULT_CACHE_TIME_UNIT);
    }

    public <T> void cacheList(List<T> list, String key, Long timeOut) {
        notEmpty(key);
        if (CollectionUtils.isEmpty(list)) {
            list = Collections.emptyList();
        }
        redisService.set(key,list,timeOut);
       // stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(list), timeOut, timeUnit);
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        notEmpty(key);
        return (List<T>) redisService.get(key);

//        String value = stringRedisTemplate.opsForValue().get(key);
//        if (StringUtils.isEmpty(value))
//            return Collections.emptyList();
//        return JSONObject.parseArray(value, clazz);
    }

    public <T> void cacheObject(T obj, String key) {
        notEmpty(key);
        redisService.set(key,obj,DEFAULT_CACHE_TIME);

       // stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(obj), DEFAULT_CACHE_TIME, DEFAULT_CACHE_TIME_UNIT);
    }

    public <T> void cacheObject(T obj, String key, Long timeOut) {
        notEmpty(key);
        redisService.set(key,obj,timeOut);
       // stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(obj), timeOut, timeUnit);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        notEmpty(key);
        return (T) redisService.get(key);
       // return JSONObject.parseObject(stringRedisTemplate.opsForValue().get(key), clazz);
    }

    public <T> void putMap(String name, Map<String, T> content, Long timeOut, TimeUnit timeUnit) {
        RMap<String, T> storeMap = redissonClient.getMap(name);
        storeMap.putAll(content);
        if (storeMap.remainTimeToLive() < 0)
            storeMap.expire(timeOut, timeUnit);
    }

    public <T> void appendMap(String name, String key, T value, Long timeOut, TimeUnit timeUnit) {
        RMap<String, T> storeMap = redissonClient.getMap(name);
        if (ObjectUtils.allNotNull(key, value))
            storeMap.fastPut(key, value);
        if (storeMap.remainTimeToLive() < 0)
            storeMap.expire(timeOut, timeUnit);
    }

    public <T> Map<String, T> getMap(String name) {
        return redissonClient.getMap(name);
    }


}

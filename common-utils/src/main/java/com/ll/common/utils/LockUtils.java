package com.ll.common.utils;

import com.smart.common.lock.client.RedissonLockClient;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author YK
 * 方法加锁工具
 */
@Component
public class LockUtils {

    //private final RedissonClient client;

    @Autowired
    private RedissonLockClient client;

    private static final String DEFAULT_LOCKED_MESSAGE = "操作繁忙，请稍后重试";

    private static final int DEFAULT_LOCK_TIME = 3;

    private static final TimeUnit DEFAULT_LOCK_UNIT = TimeUnit.MINUTES;

//    @Autowired
//    public LockHelper(RedissonClient client) {
//        this.client = client;
//    }

    @FunctionalInterface
    public interface Handler<T> {
        T execute();
    }

    @FunctionalInterface
    public interface VoidHandler {
        void execute();
    }

    public <T> T waitExecute(String key, Handler<T> handler) {
        RLock lock = getLock(key);
        lock.lock(15, TimeUnit.MINUTES);
        try {
            return handler.execute();
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public void waitExecute(String key, VoidHandler handler) {
        RLock lock = getLock(key);
        lock.lock(15, TimeUnit.MINUTES);
        try {
            handler.execute();
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }


    public <T> T execute(String key, Handler<T> handler) {
        String message = DEFAULT_LOCKED_MESSAGE;
        return execute(key, handler, message);
    }


    public <T> T execute(String key, Handler<T> handler, String lockedMessage) {
        RLock lock = getLock(key);
        if (!lock.tryLock()) {
            //todo
            //throw new BusinessException(lockedMessage);
        }
        try {
            return handler.execute();
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private RLock getLock(String key) {
        return client.getLock(key);
    }

    public void execute(String key, VoidHandler handler) {
        String message = DEFAULT_LOCKED_MESSAGE;
        execute(key, handler, message);
    }

    public void execute(String key, VoidHandler handler, String lockedMessage) {
        RLock lock = getLock(key);
        try {
            if (lock.tryLock()) {
                handler.execute();
            } else {
                //todo
                //throw new BusinessException(lockedMessage);
            }
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}

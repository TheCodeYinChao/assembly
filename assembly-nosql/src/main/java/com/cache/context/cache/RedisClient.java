package com.cache.context.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 * Created by Admin on 2019/4/5.
 */
public class RedisClient implements Cache {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public String getCache(Map<String, String> param) {
        return null;
    }

    @Override
    public void deleteCache(Map<String, String> param) {

    }

    @Override
    public void updateCache(Map<String, String> param) {

    }

    @Override
    public boolean clear(String pre) {
        return false;
    }

    @Override
    public void insertCache() {

    }
}

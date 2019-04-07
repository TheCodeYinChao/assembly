package com.cache.context.cache;

import java.util.Map;

/**
 * 缓存策略
 * Created by Admin on 2019/4/5.
 */

public class CacheContext{
    private Cache cache;

    public CacheContext(Cache cache) {
        this.cache = cache;
    }

    public String getCache(Map<String, String> param) {
        return cache.getCache(param);
    }

    public void deleteCache(Map<String, String> param) {
         cache.deleteCache(param);
    }

    public void updateCache(Map<String, String> param) {
        cache.updateCache(param);
    }

    public boolean clear(String pre) {
        return cache.clear(pre);
    }

    public void insertCache() {
        cache.insertCache();
    }
}

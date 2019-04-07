package com.cache.context.cache;

/**
 * Created by Admin on 2019/4/5.
 */
public enum  CacheEnum {
    MONGO("mongo"),REDIS("redis");




    private String cache;

    CacheEnum(String cache) {
        this.cache = cache;
    }
    public String getCache(){
        return cache;
    }
}

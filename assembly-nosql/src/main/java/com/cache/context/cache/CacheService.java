package com.cache.context.cache;

import org.springframework.stereotype.Service;

/**
 * Created by Admin on 2019/4/5.
 */
@Service
public class CacheService {

    public CacheContext operCache(CacheEnum cacheEnum){
        CacheContext c = null;
        switch (cacheEnum){
            case mongo:
                c = new CacheContext(new MongoClient());
                break;
            case redis:
                c = new CacheContext(new RedisClient());
                break;
        }
        return c;
    }

    public void t(){
        this.operCache(CacheEnum.mongo);
    }
}

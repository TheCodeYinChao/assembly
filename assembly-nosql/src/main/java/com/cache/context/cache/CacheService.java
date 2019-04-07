package com.cache.context.cache;

import org.springframework.stereotype.Service;

/**
 * Created by Admin on 2019/4/5.
 */
@Service
public class CacheService {

    public CacheContext operCache(String dataSource){
        CacheContext c = null;
        switch (dataSource){
            case Constant.MOGON:
                c = new CacheContext(new MongoClient());
                break;
            case  Constant.REDIS:
                c = new CacheContext(new RedisClient());
                break;
        }
        return c;
    }
}

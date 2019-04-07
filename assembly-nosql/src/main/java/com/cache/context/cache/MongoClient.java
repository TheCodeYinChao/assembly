package com.cache.context.cache;

import java.util.Map;

/**
 * Created by Admin on 2019/4/5.
 */
public class MongoClient implements Cache {
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

package com.cache.context.cache;

import java.util.Map;

/**
 * Created by Admin on 2019/4/5.
 */
public interface Cache {
    public String getCache(Map<String,String> param);
    public void deleteCache(Map<String,String> param);

    public void updateCache(Map<String,String> param);

    public boolean clear(String pre);

    public void insertCache();


}

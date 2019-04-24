package com.cache.redis.opea;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisOpera {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public void insert(String key ,String val){
        Boolean flag = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyByte = serializer.serialize(key);
                byte[] valByte = serializer.serialize(val);
                connection.setEx(keyByte, 50, valByte);
                return true;
            }
        });
        log.info("save result {}",flag);
    }
    protected RedisSerializer<String> getRedisSerializer() {
            return redisTemplate.getStringSerializer();
    }

    public String getVal(String key) {

        String rs = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyByte = serializer.serialize(key);
                byte[] bytes = connection.get(keyByte);
                return serializer.deserialize(bytes);
            }
        });
        log.info("get result {}",rs);
        return rs;
    }




    /**
     * 对hash类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型数据操作
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations() {
        return redisTemplate.opsForValue();
    }

    /**
     * 对链表类型的数据操作
     *
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations() {
        return redisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     *
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations() {
        return redisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     *
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations() {
        return redisTemplate.opsForZSet();
    }
}
